package com.match.matching.game;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.match.matching.Type.GameType;
import com.match.matching.Type.Line;
import com.match.matching.Type.Tier;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class LOL implements Game{
    List<ChatRoom> chatRooms  = new ArrayList<>(); //롤 현재 생성되어있는 채팅방 리스트
    @Override
    public List<ChatRoom> filteringRoom(Player player){//플레이어가 설정한 조건에 맞는 방을 검색해서 반환
        System.out.println("lol");
        List<ChatRoom> filterList =
                chatRooms.stream()
                        .filter(equalType(player))
                        .filter(equalTier(player))
                        .filter(equalLine(player))
                        .collect(Collectors.toList());

        return filterList;
    }


    public void add(ChatRoom chatRoom){
        chatRooms.add(chatRoom);
    }

    public void show(){
        chatRooms.stream().forEach(
                chatRoom -> System.out.println(chatRoom.getGameType() + " / " + chatRoom.getTier() + " / " + chatRoom.getLine()));
    }

    public void length(){
        System.out.println(chatRooms.size());
    }
    // 플레이어가 선택한 티어와 같은것을 필터링한다
    private Predicate<ChatRoom> equalTier(Player player){ // 티어별로 필터링
        return room -> room.getTier() == player.getTier();
    }
    // 플레이어가 선택한 게임타입과 같은것을 필터링한다
    private Predicate<ChatRoom> equalType(Player player){ // 게임 타입별(칼바람, 협곡)로 필터링
        return room -> room.getGameType() == player.getGameType();
    }

    private Predicate<ChatRoom> equalLine(Player player){ // 라인별로 필터링
        //TFT는 라인 개념이 없으므로 TFT일 경우는 따로 설정한다
        if(player.getGameType() == GameType.TFT) return room -> true; //라인에 상관없이 반환
        else if(player.getLine() == Line.ALL) return room -> true; // 모든라인이 가능한경우에도 라인에 상관없이 반환
        else return room -> {
                Predicate<ChatRoom> predicate = null;
                int overlapLine = room.getLineList() & player.getLineList();
                //1:탑 2:정글 3:미드 4:원딜 5:서폿
                if((overlapLine & 1) > 0 ) predicate.or(r -> r.getLine() == Line.TOP);
                if((overlapLine & 2) > 0 ) predicate.or(r -> r.getLine() == Line.JUNGLE);
                if((overlapLine & 3) > 0 ) predicate.or(r -> r.getLine() == Line.MID);
                if((overlapLine & 4) > 0 ) predicate.or(r -> r.getLine() == Line.AD);
                if((overlapLine & 5) > 0 ) predicate.or(r -> r.getLine() == Line.SUPPORTER);
                return predicate.equals(player);
            };

    }

}
