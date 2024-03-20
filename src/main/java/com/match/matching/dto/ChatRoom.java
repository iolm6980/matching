package com.match.matching.dto;

import com.match.matching.Type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private String roomId;
    private Tier tier;
    private GameType gameType;
    private Line line;
    private int lineList;
    private int maxPeople;

    public ChatRoom(Player player){
        roomId = UUID.randomUUID().toString();
        tier = player.getTier();
        gameType = player.getGameType();
        line = player.getLine();
        lineList = player.getLineList();
        maxPeople = player.getMaxPeople();
    }

//    @Builder.Default
//    private Set<WebSocketSession> sessions = new HashSet<>();


//    public void handleActions(WebSocketSession session, ChatMsg chatMsg, ChatService chatService){
//        if (chatMsg.getType().equals(ChatMsg.MessageType.ENTER)) {
//            sessions.add(session);
//            chatMsg.setMessage((chatMsg.getSender()) + "님이 입장했습니다.");
//        }
//        sendMessage(chatMsg, chatService);
//    }
//
//    public<T> void sendMessage(T message, ChatService chatService){
//        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
//    }


}
