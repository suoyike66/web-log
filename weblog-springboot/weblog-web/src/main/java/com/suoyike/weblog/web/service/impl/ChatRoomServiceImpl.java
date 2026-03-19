package com.suoyike.weblog.web.service.impl;

import com.suoyike.weblog.common.domain.dos.ChatMessageDO;
import com.suoyike.weblog.common.domain.mapper.ChatMessageMapper;
import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.enums.ChatRoomMessageTypeEnum;
import com.suoyike.weblog.web.model.vo.chatroom.ChatMessageVO;
import com.suoyike.weblog.web.model.vo.chatroom.FindChatMessagePageListReqVO;
import com.suoyike.weblog.web.model.vo.chatroom.FindChatMessagePageListRspVO;
import com.suoyike.weblog.web.model.vo.chatroom.OnlineUserVO;
import com.suoyike.weblog.web.service.ChatRoomService;
import com.suoyike.weblog.web.utils.DateTimeFormatUtil;
import com.suoyike.weblog.web.websocket.ChatWebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-03-19 14:29
 * @description: 聊天室
 **/
@Service
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    /**
     * 获取历史消息
     *
     * @param findChatMessagePageListReqVO
     * @return
     */
    @Override
    public Response<FindChatMessagePageListRspVO> findHistoryMessages(FindChatMessagePageListReqVO findChatMessagePageListReqVO) {
        // 每页展示 10 条消息
        int pageSize = 10;
        // 游标消息 ID
        Long lastId = findChatMessagePageListReqVO.getLastId();
        // 当前用户的 sessionId
        String sessionId = findChatMessagePageListReqVO.getSessionId();

        // 查询指定页码记录
        List<ChatMessageDO> messages = Objects.isNull(lastId)
                ? chatMessageMapper.selectRecentMessages(pageSize) // 如果 lastId 为 null, 说明查询的是第一页消息
                : chatMessageMapper.selectMessagesBefore(lastId, pageSize); // 若 lastId 不为 null, 则执行分页查询

        // DO 实体类转 VO 实体类
        FindChatMessagePageListRspVO vo = null;

        if (!CollectionUtils.isEmpty(messages)) {
            // 倒序变正序
            Collections.reverse(messages);

            List<ChatMessageVO> voList = messages.stream().map(chatMessageDO -> {
                // 判断是否是当前用户发送的消息
                // 如果 DO 的 qq 不为空，并且入参提交的 sessionId 和 qq 号一致，则是自己发送的消息
                boolean isSelf = StringUtils.isNotBlank(chatMessageDO.getQq())
                        && StringUtils.isNotBlank(sessionId)
                        && Objects.equals(sessionId, chatMessageDO.getQq());

                return ChatMessageVO.builder()
                        .id(chatMessageDO.getId())
                        .type(ChatRoomMessageTypeEnum.CHAT.getCode())
                        .nickname(chatMessageDO.getNickname())
                        .avatar(chatMessageDO.getAvatar())
                        .content(chatMessageDO.getContent())
                        .time(DateTimeFormatUtil.formatChatTime(chatMessageDO.getCreateTime()))
                        .isSelf(isSelf)
                        .build();
            }).collect(Collectors.toList());

            vo = FindChatMessagePageListRspVO.builder()
                    .messages(voList)
                    .hasMore(messages.size() >= pageSize) // 是否还有下一页
                    .build();
        }

        return Response.success(vo);
    }

    @Override
    public Response<List<OnlineUserVO>> findOnlineUsers() {
        // 获取所有在线用户
        List<OnlineUserVO> onlineUsers = ChatWebSocketServer.getOnlineUsers();
        return Response.success(CollectionUtils.isEmpty(onlineUsers) ? null : onlineUsers);
    }
}

