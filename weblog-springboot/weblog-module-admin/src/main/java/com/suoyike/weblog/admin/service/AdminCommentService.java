package com.suoyike.weblog.admin.service;

import com.suoyike.weblog.admin.model.vo.comment.FindCommentPageListReqVO;
import com.suoyike.weblog.common.utils.Response;

public interface AdminCommentService {

    /**
     * 查询评论分页数据
     * @param findCommentPageListReqVO
     * @return
     */
    Response findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO);

}