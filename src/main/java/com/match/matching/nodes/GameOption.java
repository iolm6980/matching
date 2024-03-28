package com.match.matching.nodes;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameOption<T, N> implements Option{
    public T option; // 내가 선택한 옵션값
    public N nextOption; // 다음 옵션
    HashMap<T, GameOption> optionMap; // 플레이어가 설정한 옵션값을 찾기위해 map으로 옵션을 저장한다
    List<String> roomList; // 리프노드에 room을 저장한다
    public GameOption(T option){
        this.option = option;
        optionMap = new HashMap<>();
        if(option instanceof Tier) roomList = new LinkedList<>();
    }

    public void addChild(GameOption gameOption){
        optionMap.put((T) gameOption.option, gameOption);
    }

    public GameOption getLastOption(Player player){ // 마지막에 있는 옵션을 가져온다
        if (option instanceof Tier) return this;

        GameOption nextOption = optionMap.get(getTField(player));
        if (nextOption != null) {
            return nextOption.getLastOption(player);
        } else {
            return null; // 더 이상 옵션이 없는 경우에는 null을 반환하여 재귀 호출 종료
        }
    }
    @Override
    public ChatRoom getRoom(Player player){
        GameOption gameOption = getLastOption(player);
        if(gameOption.roomList.size() != 0)  {
            return (ChatRoom) gameOption.roomList.get(0);
        }
        ChatRoom chatRoom = new ChatRoom(player);
        gameOption.roomList.add(chatRoom);
        return chatRoom;
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
