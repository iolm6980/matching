package com.match.matching.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.match.matching.dto.ChatMsg;
import com.match.matching.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor  {
    private final ChatService chatService;
    private final ObjectMapper objectMapper;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String roomId = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId").replace("/sub/chat/room/" , "");
        String session = (String) message.getHeaders().get("simpSessionId");
        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            System.out.println("구독" + roomId);
            chatService.plusRoomPeople(session, roomId);
            System.out.println("헤더에 session 추가" + session);
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            System.out.println("연결 해제" + roomId);
            chatService.minusRoomPeople(session);
        }
        return message;
    }
}
