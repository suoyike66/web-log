package com.suoyike.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suoyike.weblog.admin.convert.ArticleDetailConvert;
import com.suoyike.weblog.admin.event.PublishArticleEvent;
import com.suoyike.weblog.admin.event.UpdateArticleEvent;
import com.suoyike.weblog.admin.event.DeleteArticleEvent;
import com.suoyike.weblog.admin.model.vo.article.*;
import com.suoyike.weblog.admin.service.AdminArticleService;
import com.suoyike.weblog.common.domain.dos.*;
import com.suoyike.weblog.common.domain.mapper.*;
import com.suoyike.weblog.common.enums.ResponseCodeEnum;
import com.suoyike.weblog.common.exception.BizException;
import com.suoyike.weblog.common.utils.PageResponse;
import com.suoyike.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 11:23
 * @description: 文章
 **/
@Service
@Slf4j
public class AdminArticleServiceImpl implements AdminArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;
    @Autowired
    private org.springframework.context.ApplicationEventPublisher eventPublisher;

    /**
     * 发布文章
     *
     * @param publishArticleReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response publishArticle(PublishArticleReqVO publishArticleReqVO) {
        // 1. VO 转 ArticleDO, 并保存
        ArticleDO articleDO = ArticleDO.builder()
                .title(publishArticleReqVO.getTitle())
                .cover(publishArticleReqVO.getCover())
                .summary(publishArticleReqVO.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        articleMapper.insert(articleDO);

        // 拿到插入记录的主键 ID
        Long articleId = articleDO.getId();

        // 2. VO 转 ArticleContentDO，并保存
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(publishArticleReqVO.getContent())
                .build();
        articleContentMapper.insert(articleContentDO);

        // 3. 处理文章关联的分类
        Long categoryId = publishArticleReqVO.getCategoryId();

        // 3.1 校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }

        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .articleId(articleId)
                .categoryId(categoryId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 4. 保存文章关联的标签集合
        List<Long> tagIds = publishArticleReqVO.getTagIds();
        insertTagsByTagIds(articleId, tagIds);

        // 5. 发布文章发布事件
        eventPublisher.publishEvent(new PublishArticleEvent(this, articleId));

        return Response.success();
    }

    /**
     * 根据标签 ID 保存关联关系
     * @param articleId
     * @param tagIds
     */
    private void insertTagsByTagIds(Long articleId, List<Long> tagIds) {
        if (CollectionUtils.isEmpty(tagIds)) {
            return;
        }
    
        // 去除空值和重复 ID
        Set<Long> uniqueTagIds = tagIds.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
            
        if (uniqueTagIds.isEmpty()) {
            return;
        }
    
        // 查询数据库中已存在的标签
        List<TagDO> existingTags = tagMapper.selectList(
            new LambdaQueryWrapper<TagDO>().in(TagDO::getId, uniqueTagIds));
            
        if (existingTags.isEmpty()) {
            log.warn("==> 提交的标签 ID 均不存在，tagIds: {}", uniqueTagIds);
            return;
        }
    
        log.info("==> 开始保存文章 - 标签关联关系，articleId: {}, tagIds: {}", articleId, uniqueTagIds);
    
        // 创建文章 - 标签关联关系
        List<ArticleTagRelDO> articleTagRelDOS = new ArrayList<>();
        for (TagDO tagDO : existingTags) {
            ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                    .articleId(articleId)
                    .tagId(tagDO.getId())
                    .build();
            articleTagRelDOS.add(articleTagRelDO);
        }
    
        log.info("==> 准备插入的关联关系数据：{}", articleTagRelDOS);
    
        if (!articleTagRelDOS.isEmpty()) {
            // 不使用批量插入，改用逐个插入来测试
            for (ArticleTagRelDO relDO : articleTagRelDOS) {
                int count = articleTagRelMapper.insert(relDO);
                log.info("==> 插入单条记录结果：articleId={}, tagId={}, count={}", 
                    relDO.getArticleId(), relDO.getTagId(), count);
            }
        }
    }

    /**
     * 删除文章
     *
     * @param deleteArticleReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO) {
        Long articleId = deleteArticleReqVO.getId();
    
        // 1. 删除文章
        articleMapper.deleteById(articleId);
    
        // 2. 删除文章内容
        articleContentMapper.deleteByArticleId(articleId);
    
        // 3. 删除文章 - 分类关联记录
        articleCategoryRelMapper.deleteByArticleId(articleId);
    
        // 4. 删除文章 - 标签关联记录
        articleTagRelMapper.deleteByArticleId(articleId);
    
        // 5. 发布文章删除事件
        eventPublisher.publishEvent(new DeleteArticleEvent(this, articleId));
    
        return Response.success();
    }

    /**
     * 查询文章分页数据
     *
     * @param findArticlePageListReqVO
     * @return
     */
    @Override
    public Response findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = findArticlePageListReqVO.getCurrent();
        Long size = findArticlePageListReqVO.getSize();
        String title = findArticlePageListReqVO.getTitle();
        LocalDate startDate = findArticlePageListReqVO.getStartDate();
        LocalDate endDate = findArticlePageListReqVO.getEndDate();
        Integer type = findArticlePageListReqVO.getType();

        // 执行分页查询
        Page<ArticleDO> articleDOPage = articleMapper.selectPageList(current, size, title, startDate, endDate, type);

        List<ArticleDO> articleDOS = articleDOPage.getRecords();

        // DO 转 VO
        List<FindArticlePageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(articleDOS)) {
            vos = articleDOS.stream()
                    .map(articleDO -> FindArticlePageListRspVO.builder()
                            .id(articleDO.getId())
                            .title(articleDO.getTitle())
                            .cover(articleDO.getCover())
                            .createTime(articleDO.getCreateTime())
                            .isTop(articleDO.getWeight() > 0) // 是否置顶
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(articleDOPage, vos);
    }


    /**
     * 查询文章详情
     *
     * @param findArticleDetailReqVO
     * @return
     */
    @Override
    public Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO) {
        Long articleId = findArticleDetailReqVO.getId();

        ArticleDO articleDO = articleMapper.selectById(articleId);

        if (Objects.isNull(articleDO)) {
            log.warn("==> 查询的文章不存在，articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }

        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);

        // 判断文章内容是否存在
        if (Objects.isNull(articleContentDO)) {
            log.warn("==> 该文章内容不存在, articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }

        // 所属分类
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectByArticleId(articleId);

        // 对应标签
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleId(articleId);
        // 获取对应标签 ID 集合
        List<Long> tagIds = new ArrayList<>();
        if (Objects.nonNull(articleTagRelDOS)) {
            tagIds = articleTagRelDOS.stream().map(ArticleTagRelDO::getTagId).collect(Collectors.toList());
        }

        // DO 转 VO
        FindArticleDetailRspVO vo = ArticleDetailConvert.INSTANCE.convertDO2VO(articleDO);
        vo.setContent(articleContentDO.getContent());
        if (Objects.nonNull(articleCategoryRelDO)) {
            vo.setCategoryId(articleCategoryRelDO.getCategoryId());
        }
        vo.setTagIds(tagIds);

        return Response.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateArticle(UpdateArticleReqVO updateArticleReqVO) {
        Long articleId = updateArticleReqVO.getId();

        // 1. VO 转 ArticleDO, 并更新
        ArticleDO articleDO = ArticleDO.builder()
                .id(articleId)
                .title(updateArticleReqVO.getTitle())
                .cover(updateArticleReqVO.getCover())
                .summary(updateArticleReqVO.getSummary())
                .updateTime(LocalDateTime.now())
                .build();
        int count = articleMapper.updateById(articleDO);

        // 根据更新是否成功，来判断该文章是否存在
        if (count == 0) {
            log.warn("==> 该文章不存在, articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }

        // 2. VO 转 ArticleContentDO，并更新
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(updateArticleReqVO.getContent())
                .build();
        articleContentMapper.updateByArticleId(articleContentDO);


        // 3. 更新文章分类
        Long categoryId = updateArticleReqVO.getCategoryId();

        // 3.1 校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }

        // 先删除该文章关联的分类记录，再插入新的关联关系
        articleCategoryRelMapper.deleteByArticleId(articleId);
        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .articleId(articleId)
                .categoryId(categoryId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 4. 保存文章关联的标签集合
        // 先删除该文章对应的标签
        articleTagRelMapper.deleteByArticleId(articleId);
        List<Long> tagIds = updateArticleReqVO.getTagIds();
        insertTagsByTagIds(articleId, tagIds);

        // 5. 发布文章更新事件
        eventPublisher.publishEvent(new UpdateArticleEvent(this, articleId));

        return Response.success();
    }

    /**
     * 更新文章是否置顶
     *
     * @param updateArticleIsTopReqVO
     * @return
     */
    @Override
    public Response updateArticleIsTop(UpdateArticleIsTopReqVO updateArticleIsTopReqVO) {
        Long articleId = updateArticleIsTopReqVO.getId();
        Boolean isTop = updateArticleIsTopReqVO.getIsTop();

        // 默认权重为 0
        Integer weight = 0;
        // 若设置为置顶
        if (isTop) {
            // 查询出表中最大的权重值
            ArticleDO articleDO = articleMapper.selectMaxWeight();
            Integer maxWeight = articleDO.getWeight();
            // 最大权重值加一
            weight = maxWeight + 1;
        }

        // 更新该篇文章的权重值
        articleMapper.updateById(ArticleDO.builder()
                .id(articleId)
                .weight(weight)
                .build());

        return Response.success();
    }

}