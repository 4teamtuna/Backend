// 1. @EnableWebSocket 어노테이션을 사용해 웹소켓을 활성화
// 2. 특정 endpoint 를 정의 한 뒤, 도메인이 다른 서버에서도 접속 가능 하도록 모든 가능성을 열어둔다(보안성 취약함)

package com.example.gsmoa.Config;

import com.example.gsmoa.handler.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }
}
