package com.suoyike.weblog.web.service;

import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.search.SearchArticlePageListReqVO;

public interface SearchService {

    /**
     * 关键词分页搜索
     * @param searchArticlePageListReqVO
     * @return
     */
    Response searchArticlePageList(SearchArticlePageListReqVO searchArticlePageListReqVO);
}