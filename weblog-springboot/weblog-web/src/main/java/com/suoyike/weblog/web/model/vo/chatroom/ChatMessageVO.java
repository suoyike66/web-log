package com.suoyike.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 蓑衣客
 * @Date: 2026-03-19 14:10
 * @Version: v1.0.0
 * @Description: 聊天室消息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageVO {

    /**
     * 消息 ID
     */
    private Long id;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private String time;

    /**
     * 在线人数
     */
    private Integer onlineCount;
}