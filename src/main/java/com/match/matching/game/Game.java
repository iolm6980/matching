package com.match.matching.game;

import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import java.util.*;
public interface Game {
    List<ChatRoom> getFilteringRoom(Player player);
    void length();
    void add(ChatRoom chatRoom);
}
