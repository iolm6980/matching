package com.match.matching.nodes;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;

import java.util.ArrayList;
import java.util.List;
public interface GameTree {
    ChatRoom getRoom(Player player);
}
