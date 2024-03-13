package com.match.matching.game;

import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
public class Overwatch implements Game{
    @Override
    public List<ChatRoom> filteringRoom(Player player) {
        System.out.println("overwatch");
        return null;
    }

    @Override
    public void length() {

    }

    @Override
    public void add(ChatRoom chatRoom) {

    }

    public void overwatch(){
        System.out.println("overwatch");
    }
}
