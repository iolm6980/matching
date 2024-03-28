package com.match.matching.service;

import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.game.TreeFactory;
import com.match.matching.nodes.GameTree;
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
        gameTree = treeFactory.getTree(player);
        ChatRoom chatRoom = gameTree.enterRoom(player);
        return chatRoom.getRoomId();
    }

    public ChatRoom findByRoomId(String roomId){
        ChatRoom chatRoom = gameTree.findByRoomId(roomId);
        return chatRoom;
    }
    
    public void plusRoomPeople(String session ,String roomId){ // 유저가 방에 접속하면 유저의 세션과 방ID를 map에 저장한 후 방인원수 늘림
        ChatRoom room = gameTree.findByRoomId(roomId);
        room.enterPlayer(); // 현재방에 인원수를 하나 더한다.
        gameTree.add(session, room); // 나중에 세션값으로 방을 찾기위해 세션을 키로 채팅방을 저장한다
    }

    public void minusRoomPeople(String session){ // 세션을 이용해 방을 찾은 뒤 한명을 빼준다
        ChatRoom room = gameTree.findBySession(session);
        int people = room.exitPlayer(); //현재 채팅방에 인원을 하나 뺀후 인원 반환
        if(people == 0){ //채팅방에 사람이 한명도 없으면 채팅방을 삭제한다
            gameTree.remove(room.getRoomId(), session); // gameTree에 있는 roomMap과 sessionMap에 있는 값을 삭제한다
            gameTree.removeChild(room.getRoomId());
        }
    }

    public String provideName(String roomId){
        return gameTree.provideName(roomId);
    }

    public void collectName(String roomId, String name){
        gameTree.collectName(roomId, name);
    }
}
