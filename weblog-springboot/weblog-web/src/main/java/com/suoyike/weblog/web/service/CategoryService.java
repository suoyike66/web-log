package com.suoyike.weblog.web.service;

import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.model.vo.category.FindCategoryArticlePageListReqVO;
import com.suoyike.weblog.web.model.vo.category.FindCategoryListReqVO;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 15:34
 * @description: 分类
 **/
public interface CategoryService {
    /**
     * 获取分类列表
     * @return
     */
    Response findCategoryList(FindCategoryListReqVO findCategoryListReqVO);

    /**
     * 获取分类下文章分页数据
     * @param findCategoryArticlePageListReqVO
     * @return
     */
    Response findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO);
}
