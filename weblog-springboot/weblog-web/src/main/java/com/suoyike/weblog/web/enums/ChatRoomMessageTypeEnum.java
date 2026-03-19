package com.suoyike.weblog.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-03-19 14:12
 * @description: 聊天室消息类型
 **/
@Getter
@AllArgsConstructor
public enum ChatRoomMessageTypeEnum {

    SYSTEM(0, "系统消息"),
    CHAT(1, "聊天消息"),
    ONLINE_USERS(2, "在线用户列表消息");

    private Integer code;
    private String description;

}
