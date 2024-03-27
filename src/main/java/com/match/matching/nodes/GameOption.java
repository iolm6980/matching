package com.match.matching.nodes;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameOption<T> {
    T option; // 내가 선택한 옵션값
    HashMap<T, GameOption> optionMap; // 플레이어가 설정한 옵션값을 찾기위해 map으로 옵션을 저장한다
    List<ChatRoom> roomList; // 리프노드에 room을 저장한다
    public GameOption(T option){
        this.option = option;
        optionMap = new HashMap<>();
        roomList = new LinkedList<>();
    }

    public void addChild(GameOption gameOption){
        optionMap.put((T) gameOption.option, gameOption);
    }

    public GameOption getLastOption(Player player){ // 마지막에 있는 옵션을 가져온다
        if(option instanceof Tier) return this;
        else return null;
    }

    public ChatRoom add(Player player){
        GameOption gameOption = getLastOption(player);
        if(gameOption.roomList.size() != 0)  return (ChatRoom) gameOption.roomList.get(0);
        gameOption.roomList.add(player);
        return new ChatRoom(player);
    }

    public T getTFiled(Player player){ // T에 해당하는 필드를 반환한다
        if(option instanceof Game) return (T) player.getGame();
        else if(option instanceof IsRank) return (T) player.getIsRank();
        else if(option instanceof GameType) return (T) player.getGameType();
        else if(option instanceof Line) return (T) player.getLine();
        else if(option instanceof Tier) return (T) player.getTier();
        return null;
    }

}
