package com.match.matching.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig /*implements WebSocketConfigurer*/ {
//    private final WebSocketHandler webSocketHandler;
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler, "ws/chat").setAllowedOrigins("*");
//
//    }
}
