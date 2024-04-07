package com.match.matching.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.match.matching.service.ChatService;
import com.match.matching.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {
    private final FilterService filterService;
    private final ObjectMapper objectMapper;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String messageType = accessor.getFirstNativeHeader("messageType");
        messageType = messageType == null ? "null" : messageType;
        if ((StompCommand.SEND == accessor.getCommand()) && messageType.equals("MESSAGE")) {
            byte[] payloadBytes = (byte[]) message.getPayload();
            String payloadString = new String(payloadBytes, StandardCharsets.UTF_8);
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(payloadString);
                // 수정할 필드 변경
                ((ObjectNode) jsonNode).put("message", filterService.filterProfanity(jsonNode));

                // 변경된 JSON을 다시 문자열로 변환
                String modifiedPayloadString = objectMapper.writeValueAsString(jsonNode);
                // 문자열을 다시 바이트 배열로 변환하여 기존의 메시지 객체에 설정
                byte[] modifiedPayloadBytes = modifiedPayloadString.getBytes(StandardCharsets.UTF_8);
                message = MessageBuilder.createMessage(modifiedPayloadBytes, message.getHeaders());
            } catch (JsonProcessingException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
        return message;
    }

//    public String filterProfanity(String message){
//        filterService.
//    }
}
