package com.match.matching.controller;

import com.match.matching.dto.ChatMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessagingTemplate messageTemplate;

    @MessageMapping(value = "/chat/message")
    public void message(ChatMsg message){
        messageTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMsg message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        messageTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/exit")
    public void exit(ChatMsg message){
        message.setMessage(message.getWriter() + "님이 퇴장하였습니다.");
        messageTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

}
