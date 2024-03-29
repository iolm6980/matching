package com.match.matching.tree;

import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;

import java.util.HashMap;

public interface GameTree {
    HashMap<String, ChatRoom> roomMap = new HashMap<>();
    ChatRoom enterRoom(Player player);
    void removeChild(String roomId);
    String provideName(String roomId);
    void collectName(String roomId, String name);
    default ChatRoom findByRoomId(String roomId){
        return roomMap.get(roomId);
    }

    default void add(ChatRoom chatRoom){
        roomMap.put(chatRoom.getRoomId(), chatRoom);
    }

    default void remove(String roomId){
        roomMap.remove(roomId);
    }

}
