package com.suoyike.weblog.web.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: 蓑衣客
 * @Date: 2026-03-15 16:21
 * @Version: v1.0.0
 * @Description: 公共聊天室 WebSocket 服务端
 **/
@Slf4j
@Component
@ServerEndpoint("/ws/chat") // 定义 WebSocket 端点
public class ChatWebSocketServer {

    /**
     * 存储所有在线用户的会话 Session
     */
    private static final Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 在线人数
     */
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        // 获取会话 ID
        String sessionId = session.getId();
        // 保存会话，使用会话 ID 作为 Key
        SESSION_MAP.put(sessionId, session);


        // 在线人数+1
        int count = ONLINE_COUNT.incrementAndGet();

        log.info("## 用户 [sessionId:{}] 加入聊天室，当前在线人数: {}", sessionId, count);

        // 告诉客户端：你已经连接成功
        sendMessage(session, "连接成功！欢迎加入蓑衣客公共聊天室，当前在线人数：" + count);
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("## 收到用户 [sessionId:{}] 的消息: {}", session.getId(), message);

        // 发送广播，将此条消息告诉所有在线用户
        broadcastMessage(message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        String sessionId = session.getId();
        // 删除对应会话
        SESSION_MAP.remove(sessionId);

        // 在线人数-1
        int count = ONLINE_COUNT.decrementAndGet();

        log.info("## 用户 [sessionId:{}] 离开了聊天室，当前在线人数: {}", sessionId, count);

        // 发送广播，告诉所有在线用户，有人离开了
        broadcastMessage("有人离开了聊天室，当前在线人数: " + count);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("## WebSocket 连接发生错误：", error);
    }

    /**
     * 发送消息给指定客户端
     */
    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("## 发送消息失败：", e);
        }
    }

    /**
     * 广播消息给所有在线用户
     */
    private void broadcastMessage(String message) {
        SESSION_MAP.values().forEach(session -> sendMessage(session, message));
    }

}