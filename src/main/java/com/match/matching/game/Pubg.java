package com.match.matching.game;

import com.match.matching.Type.GameType;
import com.match.matching.Type.Line;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Component
public class Pubg implements Game{
    List<ChatRoom> chatRooms  = new ArrayList<>(); //현재 생성되어있는 채팅방 리스트
    @Override
    public List<ChatRoom> getFilteringRoom(Player player){//플레이어가 설정한 조건에 맞는 방을 검색해서 반환
        List<ChatRoom> filterList =
                chatRooms.stream()
                        .filter(equalRank(player))
                        .filter(equalType(player))
                        .filter(equalTier(player))
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
}
