package com.match.matching.nodes;

import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;

public interface Option {
    ChatRoom searchChatRoom(Player player);
    void deleteNode(String roomId);
}
