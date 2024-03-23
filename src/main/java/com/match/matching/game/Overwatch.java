package com.match.matching.game;

import com.match.matching.Type.GameType;
import com.match.matching.Type.Line;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class Overwatch implements Game{
    List<ChatRoom> chatRooms  = new ArrayList<>(); //현재 생성되어있는 채팅방 리스트
    @Override
    public List<ChatRoom> getFilteringRoom(Player player){//플레이어가 설정한 조건에 맞는 방을 검색해서 반환
        List<ChatRoom> filterList =
                chatRooms.stream()
                        .filter(equalRank(player))
                        .filter(equalType(player))
                        .filter(equalTier(player))
                        .filter(filterLine(player))
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

    private Predicate<ChatRoom> filterLine(Player player){ // 라인별로 필터링
        if(player.getGameType() == GameType.FREEROLE) return room -> true; //게임타입이 자유인경우 해당 필터를 무시함
        else return room -> (room.getLineList() & lineToInt(player.getLine())) > 0; //플레이어의 라인을 가진 방을 반환
    }


    private int lineToInt(Line line){// 라인을 숫자로 바꿔서 반환 0:탱커 1:딜러 2:힐러
        if(line == Line.TANKER) return (1 << 0);
        else if(line == Line.DEALER) return (1 << 1);
        else if(line == Line.HEALER) return (1 << 2);
        else return 0;
    }
}
