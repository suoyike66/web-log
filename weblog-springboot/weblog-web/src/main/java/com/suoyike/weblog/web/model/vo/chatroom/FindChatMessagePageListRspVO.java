package com.suoyike.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-03-19 14:25
 * @description: 获取历史消息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindChatMessagePageListRspVO {

    /**
     * 聊天消息
     */
    private List<ChatMessageVO> messages;

    /**
     * 是否还有下一页
     */
    private Boolean hasMore;

}
