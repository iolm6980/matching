package com.match.matching.game;

import com.match.matching.Type.GameType;
import com.match.matching.Type.Line;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class LOL implements Game{
    private Map<String, ChatRoom> chatRoomMap = new HashMap<>(); // id로 chatRoom을 찾기위한 map
    private Map<String, ChatRoom> sessionRoomMap = new HashMap<>(); // session으로 chatroom을 찾기위한 map
    @Override
    public List<ChatRoom> getFilteringRoom(Player player){//플레이어가 설정한 조건에 맞는 방을 검색해서 반환
        List<ChatRoom> filterList =
                chatRoomMap.values().stream()
                        .filter(equalRank(player))
                        .filter(equalType(player))
                        .filter(equalTier(player))
                        .filter(filterLine(player))
                        .collect(Collectors.toList());
        return filterList;
    }

    @Override
    public void add(ChatRoom chatRoom){chatRoomMap.put(chatRoom.getRoomId(), chatRoom);}

    @Override
    public void add(String session, String roomId){
        sessionRoomMap.put(session, findById(roomId));
    }

    @Override
    public ChatRoom findById(String roomId){
        return chatRoomMap.get(roomId);
    }

    @Override
    public ChatRoom findBySession(String session){
        return sessionRoomMap.get(session);
    }
    @Override
    public String provideName(String roomId){//랜덤이름을 가져온다 만약 이름이 이미 있다면 다른이름을 가져온다
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        StringBuffer bf = new StringBuffer();
        if(!((nameList & (1<<0)) > 0)) {
            bf.append("칼날부리");
            nameList = nameList | (1<<0);
        }
        else if(!((nameList & (1<<1)) > 0)){
            bf.append("돌거북");
            nameList = nameList | (1<<1);
        }
        else if(!((nameList & (1<<2)) > 0)){
            bf.append("심술두꺼비");
            nameList = nameList | (1<<2);
        }
        else if(!((nameList & (1<<3)) > 0)){
            bf.append("바위게");
            nameList = nameList | (1<<3);
        }
        else if(!((nameList & (1<<4)) > 0)){
            bf.append("어스름늑대");
            nameList = nameList | (1<<4);
        }
        chatRoom.setNameList(nameList);
        return bf.toString();
    }
    @Override
    public void collectName(String roomId, String name){
        ChatRoom chatRoom = chatRoomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        if(name.equals("칼날부리")) nameList = nameList ^ (1<<0);
        else if(name.equals("돌거북")) nameList = nameList ^ (1<<1);
        else if(name.equals("심술두꺼비")) nameList = nameList ^ (1<<2);
        else if(name.equals("바위게")) nameList = nameList ^ (1<<3);
        else if(name.equals("어스름늑대")) nameList = nameList ^ (1<<4);
        chatRoom.setNameList(nameList);
        System.out.println(name + " 회수함.....");
    }

    public void show(){
        System.out.println("현재 갯수" + chatRoomMap.values().size());
        chatRoomMap.values().stream().forEach(
                chatRoom -> System.out.println( chatRoom.getGameType() + " / " + chatRoom.getTier() + " / " + chatRoom.getLine() + " / " + Integer.toBinaryString(chatRoom.getLineList())));
    }

    private Predicate<ChatRoom> filterLine(Player player){ // 라인별로 필터링
        //TFT는 라인 개념이 없으므로 TFT일 경우는 따로 설정한다
        if(player.getGameType() == GameType.TFT) return room -> true; //라인에 상관없이 반환
        else if(player.getLine() == Line.ALL) return room -> true; // 모든라인이 가능한경우에도 라인에 상관없이 반환
        else return room -> (room.getLineList() & lineToInt(player.getLine())) > 0; //플레이어의 라인을 가진 방을 반환
    }
    
    private int lineToInt(Line line){// 라인을 숫자로 바꿔서 반환 0:탑 1:정글 2:미드 3:원딜 4:서폿
        if(line == Line.TOP) return (1 << 0);
        else if(line == Line.JUNGLE) return (1 << 1);
        else if(line == Line.MID) return (1 << 2);
        else if(line == Line.AD) return (1 << 3);
        else if(line == Line.SUPPORTER) return (1 << 4);
        else return 0;
    }
}
