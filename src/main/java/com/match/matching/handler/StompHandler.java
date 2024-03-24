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
        String line = accessor.getFirstNativeHeader("line");
        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            System.out.println("구독" + roomId);
            chatService.plusRoomPeople(session, roomId, line);
        }else if (StompCommand.SEND == accessor.getCommand()){
            System.out.println("send부분........");
            System.out.println("session.............." + session);
            System.out.println("session name.............." + chatService.getName(session));
            try {
                byte[] payloadBytes = (byte[]) message.getPayload();
                String payloadString = new String(payloadBytes, StandardCharsets.UTF_8);

                JsonNode jsonNode = objectMapper.readTree(payloadString);

                // 수정할 필드 변경
                ((ObjectNode) jsonNode).put("writer", chatService.getName(session));

                // 변경된 JSON을 다시 문자열로 변환
                String modifiedPayloadString = objectMapper.writeValueAsString(jsonNode);

                // 문자열을 다시 바이트 배열로 변환하여 기존의 메시지 객체에 설정
                byte[] modifiedPayloadBytes = modifiedPayloadString.getBytes(StandardCharsets.UTF_8);
                message = MessageBuilder.createMessage(modifiedPayloadBytes, message.getHeaders());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            System.out.println("연결 해제" + roomId);
            chatService.minusRoomPeople(session, line);
        }


        return message;
    }


}
