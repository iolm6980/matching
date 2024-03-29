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
public class StompHandler implements ChannelInterceptor  {
    private final ChatService chatService;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        String roomId = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId").replace("/sub/chat/room/" , "");
//        String session = (String) message.getHeaders().get("simpSessionId");
//        String messageType = Optional.ofNullable((String) accessor.getFirstNativeHeader("messageType")).orElse("message");
//        System.out.println("메시지 타입 " + messageType);
//        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
//            System.out.println("구독" + roomId);
//            System.out.println("헤더에 session 추가" + session);
//        } else if ((StompCommand.DISCONNECT == accessor.getCommand()) || messageType.equals("exit")) {
//            String name = accessor.getFirstNativeHeader("name");
//            System.out.println("연결 해제" + roomId);
//        }
        return message;
    }
}
