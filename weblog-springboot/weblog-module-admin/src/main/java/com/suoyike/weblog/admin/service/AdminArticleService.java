package com.suoyike.weblog.admin.service;

import com.suoyike.weblog.admin.model.vo.article.DeleteArticleReqVO;
import com.suoyike.weblog.admin.model.vo.article.FindArticlePageListReqVO;
import com.suoyike.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.suoyike.weblog.common.utils.Response;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 11:22
 * @description: 文章
 **/
public interface AdminArticleService {
    /**
     * 发布文章
     * @param publishArticleReqVO
     * @return
     */
    Response publishArticle(PublishArticleReqVO publishArticleReqVO);

    /**
     * 删除文章
     * @param deleteArticleReqVO
     * @return
     */
    Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO);

    /**
     * 查询文章分页数据
     * @param findArticlePageListReqVO
     * @return
     */
    Response findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO);

}
