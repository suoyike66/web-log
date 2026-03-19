package com.suoyike.weblog.web.service;

import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.model.vo.chatroom.FindChatMessagePageListReqVO;
import com.suoyike.weblog.web.model.vo.chatroom.FindChatMessagePageListRspVO;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-03-19 14:28
 * @description: 聊天室
 **/
public interface ChatRoomService {

    /**
     * 获取历史消息
     * @param findChatMessagePageListReqVO
     * @return
     */
    Response<FindChatMessagePageListRspVO> findHistoryMessages(FindChatMessagePageListReqVO findChatMessagePageListReqVO);
}
