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
    public void add(String session, String roomId){
        sessionRoomMap.put(session, findById(roomId));
    }

    @Override
    public String provideName(String roomId){//랜덤이름을 가져온다 만약 이름이 이미 있다면 다른이름을 가져온다
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        StringBuffer bf = new StringBuffer();
        if(!((nameList & (1<<0)) > 0)) {
            bf.append("플레이어1");
            nameList = nameList | (1<<0);
        }
        else if(!((nameList & (1<<1)) > 0)){
            bf.append("플레이어2");
            nameList = nameList | (1<<1);
        }
        else if(!((nameList & (1<<2)) > 0)){
            bf.append("플레이어3");
            nameList = nameList | (1<<2);
        }
        else if(!((nameList & (1<<3)) > 0)){
            bf.append("플레이어4");
            nameList = nameList | (1<<3);
        }
        chatRoom.setNameList(nameList);
        return bf.toString();
    }
    @Override
    public void collectName(String roomId, String name){
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        if(name.equals("플레이어1")) nameList = nameList ^ (1<<0);
        else if(name.equals("플레이어2")) nameList = nameList ^ (1<<1);
        else if(name.equals("플레이어3")) nameList = nameList ^ (1<<2);
        else if(name.equals("플레이어4")) nameList = nameList ^ (1<<3);
        chatRoom.setNameList(nameList);
        System.out.println(name + " 회수함.....");
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
