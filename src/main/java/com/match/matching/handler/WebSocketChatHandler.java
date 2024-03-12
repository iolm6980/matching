package com.match.matching.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.match.matching.dto.ChatMsg;
import com.match.matching.dto.ChatRoom;
import com.match.matching.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}" , payload);
        //TextMessage textMessage = new TextMessage("chat server");
        //session.sendMessage(textMessage);
        ChatMsg chatMsg = objectMapper.readValue(payload, ChatMsg.class);
        //ChatRoom room = chatService.findRoomById(chatMsg.getRoomId());
        //room.handleActions(session, chatMsg, chatService);
    }
}
