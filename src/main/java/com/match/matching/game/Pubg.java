package com.match.matching.game;

import com.match.matching.Type.GameType;
import com.match.matching.Type.Line;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Component
public class Pubg implements Game{
    private Map<String, ChatRoom> chatRoomMap = new HashMap<>(); // id로 chatRoom을 찾기위한 map
    private Map<String, ChatRoom> sessionRoomMap = new HashMap<>(); // session으로 chatroom을 찾기위한 map
    @Override
    public List<ChatRoom> getFilteringRoom(Player player){//플레이어가 설정한 조건에 맞는 방을 검색해서 반환
        List<ChatRoom> filterList =
                chatRoomMap.values().stream()
                        .filter(equalRank(player))
                        .filter(equalType(player))
                        .filter(equalTier(player))
                        .collect(Collectors.toList());
        return filterList;
    }

    @Override
    public void add(ChatRoom chatRoom){
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
    }

    @Override
    public void add(String session, String roomId) {

    }

    @Override
    public ChatRoom findById(String roomId){
        return chatRoomMap.get(roomId);
    }

    @Override
    public ChatRoom findBySession(String session){
        return sessionRoomMap.get(session);
    }

    public void show(){
        System.out.println("현재 갯수" + chatRoomMap.values().size());
        chatRoomMap.values().stream().forEach(
                chatRoom -> System.out.println( chatRoom.getGameType() + " / " + chatRoom.getTier() + " / " + chatRoom.getLine() + " / " + Integer.toBinaryString(chatRoom.getLineList())));
    }
}
