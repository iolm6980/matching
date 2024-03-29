package com.match.matching.nodes;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public interface GameTree {
    HashMap<String, ChatRoom> roomMap = new HashMap<>();
    HashMap<String, ChatRoom> sessionMap = new HashMap<>();
    ChatRoom enterRoom(Player player);
    void removeChild(String roomId);
    String provideName(String roomId);
    void collectName(String roomId, String name);
    default ChatRoom findByRoomId(String roomId){
        return roomMap.get(roomId);
    }
    default ChatRoom findBySession(String session){
        return sessionMap.get(session);
    };

    default void add(String session, ChatRoom chatRoom){
        sessionMap.put(session, chatRoom);
    }
    default void add(ChatRoom chatRoom){
        sessionMap.put(chatRoom.getRoomId(), chatRoom);
    }

    default void remove(String roomId, String session){
        roomMap.remove(roomId);
        sessionMap.remove(session);
    }

}
