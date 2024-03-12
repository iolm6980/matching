package com.match.matching.controller;

import com.match.matching.dto.ChatRoom;
import com.match.matching.service.ChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/matching")
    public void matching(){

    }

    @PostMapping
    public ChatRoom createRoom(@RequestParam(value = "name") String name){
        System.out.println("create Room............." + name);
        return chatService.createRoom(name);
    }

//    @GetMapping
//    public List<ChatRoom> findAllRoom(){
//        return chatService.findAllRoom();
//    }
}
