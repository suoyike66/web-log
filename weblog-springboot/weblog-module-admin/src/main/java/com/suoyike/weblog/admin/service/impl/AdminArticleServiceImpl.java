package com.suoyike.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suoyike.weblog.admin.convert.ArticleDetailConvert;
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
        List<String> publishTags = publishArticleReqVO.getTags();
        insertTags(articleId, publishTags);

        return Response.success();
    }

    /**
     * 保存标签
     * @param articleId
     * @param publishTags
     */
    private void insertTags(Long articleId, List<String> publishTags) {
        if (CollectionUtils.isEmpty(publishTags)) {
            return;
        }

        // 去除空字符串和重复标签
        Set<String> uniquePublishTags = publishTags.stream()
                .map(String::trim)
                .filter(tagName -> !tagName.isEmpty())
                .collect(Collectors.toSet());
        
        if (uniquePublishTags.isEmpty()) {
            return;
        }

        // 查询数据库中已存在的标签
        List<TagDO> existingTags = tagMapper.selectList(
            new LambdaQueryWrapper<TagDO>().in(TagDO::getName, uniquePublishTags));
        Set<String> existingTagNames = existingTags.stream()
                .map(TagDO::getName)
                .collect(Collectors.toSet());
        Map<String, Long> existingTagNameToIdMap = existingTags.stream()
                .collect(Collectors.toMap(TagDO::getName, TagDO::getId));

        // 分离需要插入的新标签和已存在的标签
        Set<String> newTagNames = uniquePublishTags.stream()
                .filter(tagName -> !existingTagNames.contains(tagName))
                .collect(Collectors.toSet());

        // 插入新标签
        List<TagDO> newTagDOS = new ArrayList<>();
        for (String tagName : newTagNames) {
            TagDO tagDO = TagDO.builder()
                    .name(tagName)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            // 先单独插入每个标签以获取其ID
            tagMapper.insert(tagDO);
            // 直接添加到映射中
            existingTagNameToIdMap.put(tagDO.getName(), tagDO.getId());
        }

        // 创建文章-标签关联关系
        List<ArticleTagRelDO> articleTagRelDOS = new ArrayList<>();
        for (String tagName : uniquePublishTags) {
            Long tagId = existingTagNameToIdMap.get(tagName);
            if (tagId != null) {
                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(tagId)
                        .build();
                articleTagRelDOS.add(articleTagRelDO);
            }
        }

        if (!articleTagRelDOS.isEmpty()) {
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
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

        // 3. 删除文章-分类关联记录
        articleCategoryRelMapper.deleteByArticleId(articleId);

        // 4. 删除文章-标签关联记录
        articleTagRelMapper.deleteByArticleId(articleId);

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

        // 执行分页查询
        Page<ArticleDO> articleDOPage = articleMapper.selectPageList(current, size, title, startDate, endDate);

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
        List<String> publishTags = updateArticleReqVO.getTags();
        insertTags(articleId, publishTags);

        return Response.success();
    }


}