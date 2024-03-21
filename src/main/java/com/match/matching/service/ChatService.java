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
import org.slf4j.Logger;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final GameFactory gameFactory;
    private Game game;
    private Map<String, ChatRoom> chatRoomMap;
    private Map<String, ChatRoom> sessionRoomMap;
    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
        sessionRoomMap = new LinkedHashMap<>();
    }
    public ChatRoom findRoomById(String id){
        return chatRoomMap.get(id);
    }

    public String enterPlayer(Player player){
        game =  gameFactory.getGame(player); // 플레이어가 선택한 게임에 따라 게임을 반환함
        List<ChatRoom> list = game.getFilteringRoom(player); // 플레이어가 선택한 조건에 맞는 리스트를 가져옴 롤에경우에는 게임과 게임타입으로 나뉜것
        if(list.size() == 0) // 만약 현재 플레이어가 입장할수 있는 방이 없으면 새로운 방을 만든다.
        {
            ChatRoom chatRoom = new ChatRoom(player);
            game.add(chatRoom);
            chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
            return chatRoom.getRoomId();
        }else{ // 만약 있다면 채팅방에 넣어준다
            return list.get(0).getRoomId();
        }
    }

    public void enterPlayer(String session ,String roomId){ // 유저가 방에 접속하면 유저의 세션과 방ID를 map에 저장한 후 방인원수 늘림
        ChatRoom room = chatRoomMap.get(roomId);
        room.enterPlayer(); // 현재방에 인원수를 하나 더한다.
        sessionRoomMap.put(session, room);
    }

    public void outPlayer(String session){ // enterPlayer 저장해놓은 세션을 이용해 방을 찾은 뒤 한명을 빼준다
        ChatRoom room = sessionRoomMap.get(session);
        room.outPlayer();
        sessionRoomMap.remove(session);
    }
}
