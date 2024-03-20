package com.match.matching.controller;
import com.match.matching.dto.ChatMsg;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.service.ChatService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;
    @GetMapping("/room")
    public void roomInfo(@RequestParam(value = "roomId") String roomId, @RequestParam(value = "line") String line, Model model) {
        System.out.println("chatRoom.............");
        model.addAttribute("room", chatService.findRoomById(roomId));
        model.addAttribute("line", line);
    }

    @GetMapping("/main")
    public void mainPage(){
    }
    @PostMapping("/match")
    public String matching(Player player){
        System.out.println("player........" + player.getGame() + " / " +player.getGameType() + " / " + player.getTier() + " / " + Integer.toBinaryString(player.getLineList()));
        String roomId = chatService.enterPlayer(player);
        return "redirect:/chat/room?roomId=" + roomId + "&line=" + player.getLine();
    }
}