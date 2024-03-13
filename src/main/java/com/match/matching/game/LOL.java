package com.match.matching.game;

import com.match.matching.Type.GameType;
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
//                        .filter(equalTier(player))
//                        .filter(equalLine(player))
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
        return room -> (player.getGameType() == GameType.TFT) ? true : room.getLine() == player.getLine();
    }
}
