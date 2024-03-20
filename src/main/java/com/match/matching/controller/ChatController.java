package com.match.matching.controller;

import com.match.matching.dto.ChatMsg;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.service.ChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;
    private final SimpMessageSendingOperations messageTemplate;
    @PostMapping("/match")
    public String matching(Player player){
        System.out.println("player........" + player.getGame() + " / " +player.getGameType() + " / " + player.getTier() + " / " + Integer.toBinaryString(player.getLineList()));
        chatService.enterPlayer(player);
        return "redirect:/chat/main";
    }

    @MessageMapping("/chat/message")
    public void message(ChatMsg message){
        messageTemplate.convertAndSend("/sub/chat/room" + message.getRoomId(), message);
    }


    @GetMapping("/main")
    public void mainPage(){
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
