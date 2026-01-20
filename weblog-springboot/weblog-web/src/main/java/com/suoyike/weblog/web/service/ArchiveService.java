package com.suoyike.weblog.web.service;

import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.model.vo.archive.FindArchiveArticlePageListReqVO;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-20 17:32
 * @description: 归档文章
 **/
public interface ArchiveService {
    /**
     * 获取文章归档分页数据
     * @param findArchiveArticlePageListReqVO
     * @return
     */
    Response findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO);
}