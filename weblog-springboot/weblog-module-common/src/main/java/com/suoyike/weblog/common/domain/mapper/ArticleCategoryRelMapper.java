package com.suoyike.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.suoyike.weblog.common.domain.dos.ArticleCategoryRelDO;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 11:15
 * @description: 文章分类关联
 **/
public interface ArticleCategoryRelMapper extends BaseMapper<ArticleCategoryRelDO> {

    /**
     * 根据文章 ID 删除关联记录
     * @param articleId
     * @return
     */
    default int deleteByArticleId(Long articleId) {
        return delete(Wrappers.<ArticleCategoryRelDO>lambdaQuery()
                .eq(ArticleCategoryRelDO::getArticleId, articleId));
    }
}
