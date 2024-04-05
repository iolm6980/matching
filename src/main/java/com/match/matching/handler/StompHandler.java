package com.match.matching.handler;

import com.match.matching.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {
    private final ChatService chatService;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String roomId = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId").replace("/sub/chat/room/" , "");
        String userName = accessor.getFirstNativeHeader("userName");
        if ((StompCommand.DISCONNECT == accessor.getCommand())) {
            System.out.println("사용자 이름 " + userName);
            //chatService.exitPlayer(roomId, );
        }
        return message;
    }
}
