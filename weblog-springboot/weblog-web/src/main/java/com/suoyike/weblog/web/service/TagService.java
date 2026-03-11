package com.suoyike.weblog.web.service;

import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.model.vo.tag.FindTagArticlePageListReqVO;
import com.suoyike.weblog.web.model.vo.tag.FindTagListReqVO;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2023-09-15 14:03
 * @description: 分类
 **/
public interface TagService {
    /**
     * 获取标签列表
     * @return
     */
    Response findTagList(FindTagListReqVO findTagListReqVO);

    /**
     * 获取标签下文章分页列表
     * @param findTagArticlePageListReqVO
     * @return
     */
    Response findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO);
}
