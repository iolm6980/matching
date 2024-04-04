package com.match.matching.node;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;

import java.util.*;

public class GameOption<T, N> implements Option {
    private T option; // 내가 선택한 옵션값
    private N nextOption; // 다음 옵션
    HashMap<T, GameOption> optionMap = new HashMap<>();; // 플레이어가 설정한 옵션값을 찾기위해 map으로 옵션을 저장한다
    HashMap<String, ChatRoom> roomMap = new LinkedHashMap<>();; // 리프노드에 room을 저장한다
    public GameOption(T option){
        this.option = option;
    }

    public void addChild(GameOption gameOption){
        optionMap.put((T) gameOption.option, gameOption);
    }

    public GameOption getLastOption(Player player) { // 마지막에 있는 roomMap을 가져오기 위해 맨마지막에 있는 옵션을 가져온다
        GameOption currentOption = this;
        while (currentOption != null && !currentOption.optionMap.isEmpty()) {
            Object nextOptionValue = currentOption.getTField(player);
            currentOption = (GameOption) currentOption.optionMap.get(nextOptionValue);
        }
        return currentOption;
    }
    @Override
    public ChatRoom searchChatRoom(Player player, int line){ // 플레이어가 설정한 조건의 방이 있는지 확인한다 없으면 새로운 방을 만든다
        Tier saveTier = player.getTier();
        GameOption gameOption = getLastOption(player);
        ChatRoom room = tierSearch(player, line);

        if (room == null && player.getTier() != Tier.CHALLENGER) { //방이 없으면 현재 티어보다 하나 높은곳에서 검색
            player.setTier(Tier.UpTier(player.getTier()));
            System.out.println("업 탐색" + player.getTier());
            room = tierSearch(player, line);
        }
        player.setTier(saveTier);
        if (room == null && player.getTier() != Tier.BRONZE) {//방이 없으면 현재 티어보다 하나 낮은곳에서 검색
            player.setTier(Tier.DownTier(player.getTier()));
            System.out.println("다운 탐색" + player.getTier());
            room = tierSearch(player, line);
        }
        player.setTier(saveTier);
        if(room == null) { // 그래도 방이없으면 방을 하나 새로 만든다
            System.out.println("새로하나만듬");
            room = new ChatRoom(player);
            gameOption.roomMap.put(room.getRoomId(), room);
        }
        return room;


    }
    public ChatRoom tierSearch(Player player, int line){ // 현재 리프노드에서 겹치는 라인이 없고 방이 가득차있지 않은방을 찾아 반환한다
        GameOption gameOption = getLastOption(player);
        ChatRoom room = null;
        if(!gameOption.roomMap.isEmpty())  {
            for (Object key : gameOption.roomMap.keySet()) {
                room = (ChatRoom) gameOption.roomMap.get(key);
                if(((room.getLineList() & line) > 0) && (room.getCurrentPlayer() < room.getMaxPlayer())) return room;
            }
        }
        return null;
    }
    public T getOption(){
        return option;
    }

    public void setNextOption(N nextOption){
        this.nextOption = nextOption;
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
