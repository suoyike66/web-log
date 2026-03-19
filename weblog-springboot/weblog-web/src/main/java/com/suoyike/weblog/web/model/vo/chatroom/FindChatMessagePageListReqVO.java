package com.suoyike.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-03-19 14:24
 * @description: 获取历史消息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindChatMessagePageListReqVO {

    /**
     * 游标 ID
     */
    private Long lastId;

}