package com.suoyike.weblog.web.service;

import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.model.vo.comment.FindQQUserInfoReqVO;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-03-15 15:42
 * @description: 评论
 **/
public interface CommentService {

    /**
     * 根据 QQ 号获取用户信息
     * @param findQQUserInfoReqVO
     * @return
     */
    Response findQQUserInfo(FindQQUserInfoReqVO findQQUserInfoReqVO);

}