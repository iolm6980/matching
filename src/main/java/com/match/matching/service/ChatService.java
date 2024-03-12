package com.match.matching.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.match.matching.Type.Game;
import com.match.matching.Type.GameType;
import com.match.matching.Type.Tier;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
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

    @PostConstruct
    private void init(){
        chatRooms = new Vector<>();
        IntStream.rangeClosed(1, 30).forEach(
                i -> {
                    ChatRoom chatRoom = ChatRoom.builder()
                            .name(String.valueOf(i))
                            .game(Game.LOL)
                            .tier(Tier.getRandom())
                            .gameType(GameType.getRandom())
                            .build();
                    chatRooms.add(chatRoom);
                }
        );
    }
//    public List<ChatRoom> findAllRoom(){
//        return new ArrayList<>(chatRooms.values());
//    }

//    public ChatRoom findRoomById(String roomId){
//        return chatRooms.get(roomId);
//    }

    public ChatRoom createRoom(String name){
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.add(chatRoom);
        return chatRoom;
    }
    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }
    }

    public List enterPlayer(Player player){

        List<ChatRoom> rooms = chatRooms.stream()
                                .filter(room -> room.getGame() == player.getGame())
                                .filter(room -> true)
                                .filter(room -> room.getTier() == player.getTier()).collect(Collectors.toList());
        return rooms;
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
