package com.match.matching.dto;

import com.match.matching.Type.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private String roomId;
    private Game game;
    private Tier tier;
    private IsRank isRank;
    private GameType gameType;
    private Line line;
    private int lineList;
    private int maxPlayer;
    private int currentPlayer;
    private int nameList;

    public ChatRoom(Player player){
        roomId = UUID.randomUUID().toString();
        game = player.getGame();
        tier = player.getTier();
        isRank = player.getIsRank();
        gameType = player.getGameType();
        line = player.getLine();
        lineList = player.getLineList();
        currentPlayer = 0;
        maxPlayer = setMaxPeople(player);
        nameList = 0;

    }
    public int setMaxPeople(Player player){
        switch (player.getGameType()){
            case DUO: return 2;
            case TRIO: return 3;
            case TEAM, ARAM:
                if(player.getGame() == Game.PUBG) return 4;
                else return 5;
            case FIXEDROLE, FREEROLE: return 6;
            case TFT: return 8;
            default: return 0;
        }
    }
    public void plusPlayer(){
        currentPlayer++;
    }
    public int minusPlayer(){
        return --currentPlayer;
    }

    public void setNameList(int nameList){
        this.nameList = nameList;
    }
}
