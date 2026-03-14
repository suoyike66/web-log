package com.suoyike.weblog.admin.service;

import com.suoyike.weblog.admin.model.vo.wiki.AddWikiReqVO;
import com.suoyike.weblog.common.utils.Response;

public interface AdminWikiService {

    /**
     * 新增知识库
     * @param addWikiReqVO
     * @return
     */
    Response addWiki(AddWikiReqVO addWikiReqVO);
}