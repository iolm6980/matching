package com.match.matching.nodes;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;

import java.lang.reflect.Field;
import java.util.*;

public class GameOption<T, N> implements Option{
    T option; // 내가 선택한 옵션값
    N nextOption; // 다음 옵션
    HashMap<T, GameOption> optionMap; // 플레이어가 설정한 옵션값을 찾기위해 map으로 옵션을 저장한다
    HashMap<String, ChatRoom> roomMap; // 리프노드에 room을 저장한다
    public GameOption(T option){
        this.option = option;
        optionMap = new HashMap<>();
        roomMap = new LinkedHashMap<>();
    }

    public void addChild(GameOption gameOption){
        optionMap.put((T) gameOption.option, gameOption);
    }

    public GameOption getLastOption(Player player) { // 맨마지막에 있는 옵션을 가져온다
        GameOption currentOption = this;
        while (currentOption != null && !currentOption.optionMap.isEmpty()) {
            Object nextOptionValue = currentOption.getTField(player);
            currentOption = (GameOption) currentOption.optionMap.get(nextOptionValue);
        }
        return currentOption;
    }
    @Override
    public ChatRoom searchChatRoom(Player player, int line){ // 플레이어가 설정한 조건의 방이 있는지 확인한다 없으면 새로운 방을 만든다
        GameOption gameOption = getLastOption(player);
        ChatRoom room = null;
        if(!gameOption.roomMap.isEmpty())  {
            for (Object key : gameOption.roomMap.keySet()) {
                room = (ChatRoom) gameOption.roomMap.get(key);
                System.out.println(Integer.toBinaryString(room.getLineList()) + " / " + Integer.toBinaryString(line));
                if((room.getLineList() & line) > 0) return room;
            }
        }
        room = new ChatRoom(player);
        gameOption.roomMap.put(room.getRoomId(), room);
        return room;
    }

    @Override
    public void removeNode(String roomId) {
        roomMap.remove(roomId);
    }

    public N getTField(Player player){ // N에 해당하는 필드를 반환한다
        if(nextOption instanceof Game) return (N) player.getGame();
        else if(nextOption instanceof IsRank) return (N) player.getIsRank();
        else if(nextOption instanceof GameType) return (N) player.getGameType();
        else if(nextOption instanceof Line) return (N) player.getLine();
        else if(nextOption instanceof Tier) return (N) player.getTier();
        return null;
    }

}
