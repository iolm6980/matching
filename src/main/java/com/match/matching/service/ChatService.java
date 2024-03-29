package com.match.matching.service;

import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.tree.TreeFactory;
import com.match.matching.tree.GameTree;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final TreeFactory treeFactory;
    private GameTree gameTree;
    public String enterPlayer(Player player){
        gameTree = treeFactory.getTree(player); // 게임에 맞는 트리 반환
        ChatRoom chatRoom = gameTree.enterRoom(player); // 트리에 조건에 맞는 방이 있는지 확인 없으면 새로운 채팅방을 생성후 반환
        chatRoom.plusPlayer(); //방 인원수 증가
        return chatRoom.getRoomId();
    }

    public void exitPlayer(String roomId, String name){
        ChatRoom chatRoom = gameTree.findByRoomId(roomId);
        int people = chatRoom.minusPlayer();
        gameTree.collectName(roomId, name); // 부여받은 이름을 회수
        if(people == 0){
            gameTree.remove(chatRoom.getRoomId()); // gameTree에 있는 roomMap에 있는 값을 삭제한다
            gameTree.removeChild(chatRoom.getRoomId());
        }
    }

    public ChatRoom findByRoomId(String roomId){
        ChatRoom chatRoom = gameTree.findByRoomId(roomId);
        return chatRoom;
    }

    public void add(ChatRoom chatRoom){
        gameTree.add(chatRoom);
    }

    public String provideName(String roomId){
        return gameTree.provideName(roomId); // 이름을 부여받음
    }
}
