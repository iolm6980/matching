package com.match.matching.dto;

import com.match.matching.Type.*;
import com.match.matching.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private String roomId;
    private String name;
    private Game game;
    private Tier tier;
    private GameType gameType;
    private Line line;
    private int lineList;
    private int maxPeople;
    @Builder.Default
    private Set<WebSocketSession> sessions = new HashSet<>();


    public void handleActions(WebSocketSession session, ChatMsg chatMsg, ChatService chatService){
        if (chatMsg.getType().equals(ChatMsg.MessageType.ENTER)) {
            sessions.add(session);
            chatMsg.setMessage((chatMsg.getSender()) + "님이 입장했습니다.");
        }
        sendMessage(chatMsg, chatService);
    }

    public<T> void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }

    public void enterPlayer(){

    }


}
