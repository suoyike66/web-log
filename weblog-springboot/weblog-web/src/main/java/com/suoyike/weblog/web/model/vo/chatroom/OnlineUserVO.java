package com.suoyike.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 蓑衣客
 * @Date: 2026-03-19 14:19
 * @Version: v1.0.0
 * @Description: 在线用户
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlineUserVO {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否在线
     */
    private Boolean online;
}
