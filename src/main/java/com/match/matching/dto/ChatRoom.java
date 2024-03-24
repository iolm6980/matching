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
    private Rank rank;
    private GameType gameType;
    private Line line;
    private int lineList;
    private int maxPlayer;
    private int currentPlayer;
    private int nameList;
    private HashMap<String, String> sessionMap;

    public ChatRoom(Player player){
        roomId = UUID.randomUUID().toString();
        game = player.getGame();
        tier = player.getTier();
        rank = player.getRank();
        gameType = player.getGameType();
        line = player.getLine();
        lineList = player.getLineList();
        currentPlayer = 0;
        maxPlayer = setMaxPeople(player);
        nameList = 0;
        sessionMap = new HashMap<>();
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

    public void enterPlayer(String session){
        currentPlayer++;
        String name = provideName();
        sessionMap.put(session, name);
        System.out.println("session " + session + "에게" + name + "제공함");
        sessionMap.get(session);
    }
    public void exitPlayer(String session){
        currentPlayer--;
        collectName(sessionMap.get(session));
        sessionMap.remove(session);
    }
    public String getName(String session){
        return sessionMap.get(session);
    }

    public String provideName(){//랜덤이름을 가져온다 만약 이름이 이미 있다면 다른이름을 가져온다
        String name = null;
        if(!((nameList & (1<<0)) > 0)) {
            name = "칼날부리";
            nameList = nameList | (1<<0);
        }
        else if(!((nameList & (1<<1)) > 0)){
            name = "돌거북";
            nameList = nameList | (1<<1);
        }
        else if(!((nameList & (1<<2)) > 0)){
            name = "심술두꺼비";
            nameList = nameList | (1<<2);
        }
        else if(!((nameList & (1<<3)) > 0)){
            name = "바위게";
            nameList = nameList | (1<<3);
        }
        else if(!((nameList & (1<<4)) > 0)){
            name = "어스름늑대";
            nameList = nameList | (1<<4);
        }
        return name;
    }

    public void collectName(String name){
        if(name.equals("칼날부리")) nameList = nameList ^ (1<<0);
        else if(name.equals("돌거북")) nameList = nameList ^ (1<<1);
        else if(name.equals("심술두꺼비")) nameList = nameList ^ (1<<2);
        else if(name.equals("바위게")) nameList = nameList ^ (1<<3);
        else if(name.equals("어스름늑대")) nameList = nameList ^ (1<<4);
        System.out.println(name + " 회수함.....");
    }
}
