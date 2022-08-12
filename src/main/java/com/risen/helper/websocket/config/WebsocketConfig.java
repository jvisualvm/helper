package com.risen.helper.websocket.config;

import com.risen.helper.websocket.handler.WebSocketHandler;
import com.risen.helper.websocket.interceptor.WsSocketInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/3 13:52
 */
@Configuration
@EnableWebSocket
@ConditionalOnProperty(prefix = "helper.websocket", name = "switch",havingValue = "true")
public class WebsocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(), "/ws")
                .addInterceptors(new WsSocketInterceptor())
                .setAllowedOrigins("*");
    }

}
