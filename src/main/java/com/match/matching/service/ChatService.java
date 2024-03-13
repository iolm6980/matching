package com.match.matching.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.match.matching.Type.GameType;
import com.match.matching.Type.Tier;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.game.Game;
import com.match.matching.game.GameFactory;
import com.match.matching.game.LOL;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Vector<ChatRoom> chatRooms;
    private final GameFactory gameFactory;
    private List<Player> list = new ArrayList<>();
    private Game game;

//    public List<ChatRoom> findAllRoom(){
//        return new ArrayList<>(chatRooms.values());
//    }

//    public ChatRoom findRoomById(String roomId){
//        return chatRooms.get(roomId);
//    }

    public ChatRoom createRoom(Player player){
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .game(player.getGame())
                .gameType(player.getGameType())
                .tier(player.getTier())
                .line(player.getLine())
                .build();
        return chatRoom;
    }
    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }
    }

    public void enterPlayer(Player player){
        game =  gameFactory.getGame(player); // 플레이어가 선택한 게임에 따라 게임을 반환함
        List<ChatRoom> list = game.filteringRoom(player); // 플레이어가 선택한 조건에 맞는 리스트를 가져옴 롤에경우에는 게임과 게임타입으로 나뉜것
        if(list.size() == 0) // 만약 현재 플레이어가 입장할수 있는 방이 없으면 새로운 방을 만든다.
        {
            ChatRoom chatRoom = createRoom(player);
            game.add(chatRoom);
        }else{ // 만약 있다면 인원수나 라인을 매칭하는 알고리즘을 써 채팅방에 넣어준다

        }
    }
    public void seeList(List<ChatRoom> list){
        list.stream().forEach(room ->{
            System.out.println(room.getGame() + " / "+room.getTier() + " / " + room.getGameType());
        });
    }

    public void seeAllList(){
        chatRooms.stream().forEach(room ->{
            System.out.println(room.getGame() + " / "+room.getTier() + " / " + room.getGameType());
        });
    }
}
