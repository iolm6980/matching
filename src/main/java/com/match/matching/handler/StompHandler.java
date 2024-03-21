package com.match.matching.handler;

import com.match.matching.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor  {
    private final ChatService chatService;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String roomId = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId").replace("/sub/chat/room/" , "");
        String session = (String) message.getHeaders().get("simpSessionId");
        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            System.out.println("구독" + roomId);
            chatService.enterPlayer(session, roomId);
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            System.out.println("연결 해제" + roomId);
            chatService.outPlayer(session);
        }

        return message;
    }
}
