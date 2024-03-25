package com.match.matching.controller;
import com.match.matching.dto.ChatMsg;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.socket.WebSocketSession;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;
    @GetMapping("/room")
    public void roomInfo(@RequestParam(value = "roomId") String roomId, Model model) {
        System.out.println("chatRoom.............");
        String username = chatService.provideName(roomId);
        System.out.println("부여받은 이름 " +  username);
        model.addAttribute("room", chatService.findRoomById(roomId));
        model.addAttribute("username", username);
    }

    @PostMapping("/quit")
    public String quit(@RequestParam(value = "roomId") String roomId, @RequestParam(value = "username") String username){
        System.out.println("나가기 " + username + " / " + roomId);
        chatService.collectName(roomId, username);
        return "redirect:/chat/main";
    }

    @GetMapping("/main")
    public void mainPage(){
    }
    @PostMapping("/match")
    public String matching(Player player, HttpServletResponse response) throws IOException {
        System.out.println("player........" + player.getGame() + " / " +player.getGameType() + " / " + player.getTier() + " / " + Integer.toBinaryString(player.getLineList()));
        String roomId = chatService.enterPlayer(player);
        return "redirect:/chat/room?roomId=" + roomId;
    }

    @GetMapping("/player/{roomId}")
    public ResponseEntity<Integer> people(@PathVariable(value = "roomId") String roomId){ // 현재 방에 있는 사용자 수를 반환한다
        Integer currentPeople;
        try {
            currentPeople = chatService.findRoomById(roomId).getCurrentPlayer();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(currentPeople, HttpStatus.OK);
    }
}