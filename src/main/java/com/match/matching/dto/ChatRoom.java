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
    private int currentPeople;
    public ChatRoom(Player player){
        roomId = UUID.randomUUID().toString();
        tier = player.getTier();
        gameType = player.getGameType();
        line = player.getLine();
        lineList = player.getLineList();
        currentPeople = 0;
        maxPeople = setMaxPeople(player.getGameType());
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
        currentPeople++;
    }
    public void outPlayer(){currentPeople--;}


}
