package com.suoyike.weblog.web.controller;

import com.suoyike.weblog.common.aspect.ApiOperationLog;
import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.model.vo.chatroom.FindChatMessagePageListReqVO;
import com.suoyike.weblog.web.model.vo.chatroom.FindChatMessagePageListRspVO;
import com.suoyike.weblog.web.service.ChatRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-03-19 14:30
 * @description: 聊天室
 **/
@RestController
@RequestMapping("/chat")
@Api(tags = "聊天室")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/message/history")
    @ApiOperation(value = "获取历史消息")
    @ApiOperationLog(description = "获取历史消息")
    public Response<FindChatMessagePageListRspVO> findHistoryMessages(@RequestBody @Validated FindChatMessagePageListReqVO findChatMessagePageListReqVO) {
        return chatRoomService.findHistoryMessages(findChatMessagePageListReqVO);
    }

}