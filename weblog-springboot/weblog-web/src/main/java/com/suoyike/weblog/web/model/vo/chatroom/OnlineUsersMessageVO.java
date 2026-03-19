package com.suoyike.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: 蓑衣客
 * @Date: 2026-03-19 14:21
 * @Version: v1.0.0
 * @Description: 在线用户列表消息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlineUsersMessageVO {

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 在线用户列表
     */
    private List<OnlineUserVO> users;

    /**
     * 在线人数
     */
    private Integer onlineCount;
}