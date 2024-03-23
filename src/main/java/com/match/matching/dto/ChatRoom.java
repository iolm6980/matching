package com.match.matching.dto;

import com.match.matching.Type.*;
import lombok.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private String roomId;
    private Game game;
    private Tier tier;
    private GameType gameType;
    private Line line;
    private int lineList;
    private int maxPlayer;
    private int currentPlayer;
    private HashMap<String, String> sessionMap;

    public ChatRoom(Player player){
        roomId = UUID.randomUUID().toString();
        game = player.getGame();
        tier = player.getTier();
        gameType = player.getGameType();
        line = player.getLine();
        lineList = player.getLineList();
        currentPlayer = 0;
        maxPlayer = setMaxPeople(player.getGameType());
        sessionMap = new HashMap<>();
    }

    public int setMaxPeople(GameType gameType){
        switch (gameType){
            case DOURANK: return 2;
            case TEAMRANK, NORMAL, ARAM: return 5;
            case TFT: return 8;
            default: return 0;
        }
    }

    public void enterPlayer(){
        currentPlayer++;
    }
    public void exitPlayer(){currentPlayer--;}


}
