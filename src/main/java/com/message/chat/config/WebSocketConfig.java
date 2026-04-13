package com.message.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker   // turns on STOMP over WebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // This is the URL clients connect to to open the WebSocket
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*")  // allow all origins (dev only)
                .withSockJS();                   // fallback to long-polling if WebSocket unavailable
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Messages sent TO /app/... are routed to @MessageMapping methods
        registry.setApplicationDestinationPrefixes("/app");

        // Messages sent to /topic/... are broadcast to all subscribers
        // Messages sent to /queue/... are sent to a specific user (private)
        registry.enableSimpleBroker("/topic", "/queue");
    }
}
