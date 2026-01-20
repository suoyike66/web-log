package com.suoyike.weblog.admin.convert;

import com.suoyike.weblog.admin.model.vo.article.FindArticleDetailRspVO;
import com.suoyike.weblog.common.domain.dos.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026/01/20 13:04
 * @description: 文章详情转换
 **/
@Mapper
public interface ArticleDetailConvert {
    /**
     * 初始化 convert 实例
     */
    ArticleDetailConvert INSTANCE = Mappers.getMapper(ArticleDetailConvert.class);

    /**
     * 将 DO 转化为 VO
     * @param bean
     * @return
     */
    FindArticleDetailRspVO convertDO2VO(ArticleDO bean);

}
