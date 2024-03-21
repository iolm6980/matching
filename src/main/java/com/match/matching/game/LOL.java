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
    public List<ChatRoom> getFilteringRoom(Player player){//플레이어가 설정한 조건에 맞는 방을 검색해서 반환
        List<ChatRoom> filterList =
                chatRooms.stream()
                        .filter(equalType(player))
                        .filter(equalTier(player))
                        .filter(filterLine(player))
                        //.filter(filterPerson(player)) // 구하는 인원과 현재 인원이 같은것들을 반환
                        //인원수 별로 핉터링
                        //현재 있는 방에서 조건을 만족할수있는 방이 있으면 방을 합침
                        .collect(Collectors.toList());

        return filterList;
    }


    public void add(ChatRoom chatRoom){
        chatRooms.add(chatRoom);
    }

    public void show(){
        chatRooms.stream().forEach(
                chatRoom -> System.out.println( chatRoom.getGameType() + " / " + chatRoom.getTier() + " / " + chatRoom.getLine() + " / " + Integer.toBinaryString(chatRoom.getLineList())));
    }

    public void length(){
        System.out.println(chatRooms.size());
    }

    private Predicate<ChatRoom> equalTier(Player player){ // 플레이어가 선택한 티어와 같은것을 필터링한다
        return room -> room.getTier() == player.getTier();
    }

    private Predicate<ChatRoom> equalType(Player player){ // 게임 타입별(칼바람, 협곡)로 필터링
        return room -> room.getGameType() == player.getGameType();
    }

    private Predicate<ChatRoom> filterLine(Player player){ // 라인별로 필터링
        //TFT는 라인 개념이 없으므로 TFT일 경우는 따로 설정한다
        if(player.getGameType() == GameType.TFT) return room -> true; //라인에 상관없이 반환
        else if(player.getLine() == Line.ALL) return room -> true; // 모든라인이 가능한경우에도 라인에 상관없이 반환
        else return room -> (room.getLineList() & lineToInt(player.getLine())) > 0; //플레이어의 라인을 가진 방을 반환
    }

//    private Predicate<ChatRoom> filterPerson(Player player){
//        return r-> ((player.getCurrentPerson() == r.getNeedPerson()) && (player.getNeedPerson() == r.getCurrentPerson()));
//    }
    
    private int lineToInt(Line line){// 라인을 숫자로 바꿔서 반환 0:탑 1:정글 2:미드 3:원딜 4:서폿
        if(line == Line.TOP) return (1 << 0);
        else if(line == Line.JUNGLE) return (1 << 1);
        else if(line == Line.MID) return (1 << 2);
        else if(line == Line.AD) return (1 << 3);
        else if(line == Line.SUPPORTER) return (1 << 4);
        else return 0;
    }
}
