package com.match.matching.controller;

import com.match.matching.dto.ChatMsg;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.service.ChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessagingTemplate messageTemplate;

    @MessageMapping(value = "/chat/message")
    public void message(ChatMsg message){
        System.out.println("메세지 받음................" + message.getMessage() + " / " + message.getRoomId());
       // message.setMessage(message.getMessage()); //이거 지우면 메시지가 화면에 출력이 안된다 setAllowedOrigins -> setAllowedOriginPatterns 로 바꿔줬더니 지워도 실행이 된다
        messageTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMsg message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        messageTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }




//    @PostMapping
//    public ChatRoom createRoom(@RequestParam(value = "name") String name){
//        System.out.println("create Room............." + name);
//        return chatService.createRoom(name);
//    }

//    @GetMapping
//    public List<ChatRoom> findAllRoom(){
//        return chatService.findAllRoom();
//    }
}
