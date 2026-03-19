package com.suoyike.weblog.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: 蓑衣客
 * @Date: 2026-03-16 16:19
 * @Version: v1.0.0
 * @Description: WebSocket 配置类
 **/
@Configuration
public class WebSocketConfig {

    /**
     * 注册 WebSocket 端点（使用 @ServerEndpoint 注解声明的方式）
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}