package com.suoyike.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.suoyike.weblog.common.domain.dos.ArticleDO;
import com.suoyike.weblog.common.domain.mapper.ArticleMapper;
import com.suoyike.weblog.common.domain.mapper.CategoryMapper;
import com.suoyike.weblog.common.domain.mapper.TagMapper;
import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.model.vo.statistics.FindStatisticsInfoRspVO;
import com.suoyike.weblog.web.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-02-01 13:31
 * @description: 统计信息
 **/
@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;

    /**
     * 获取文章总数、分类总数、标签总数、总访问量统计信息
     *
     * @return
     */
    @Override
    public Response findInfo() {
        // 查询文章总数
        Long articleTotalCount = articleMapper.selectCount(Wrappers.emptyWrapper());

        // 查询分类总数
        Long categoryTotalCount = categoryMapper.selectCount(Wrappers.emptyWrapper());

        // 查询标签总数
        Long tagTotalCount = tagMapper.selectCount(Wrappers.emptyWrapper());

        // 总浏览量
        List<ArticleDO> articleDOS = articleMapper.selectAllReadNum();
        Long pvTotalCount = 0L;

        if (!CollectionUtils.isEmpty(articleDOS)) {
            // 所有 read_num 相加
            pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
        }

        // 组装 VO 类
        FindStatisticsInfoRspVO vo = FindStatisticsInfoRspVO.builder()
                .articleTotalCount(articleTotalCount)
                .categoryTotalCount(categoryTotalCount)
                .tagTotalCount(tagTotalCount)
                .pvTotalCount(pvTotalCount)
                .build();

        return Response.success(vo);
    }
}