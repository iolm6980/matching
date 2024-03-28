package com.match.matching.nodes;

import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;

public interface Option {
    ChatRoom getRoom(Player player);
}
