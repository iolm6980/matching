package com.match.matching.game;

import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import java.util.*;
import java.util.function.Predicate;

public interface Game {
    List<ChatRoom> getFilteringRoom(Player player);

    ChatRoom findById(String roomId);
    ChatRoom findBySession(String session);
    void add(ChatRoom chatRoom);

    default Predicate<ChatRoom> equalTier(Player player){ // 플레이어가 선택한 티어와 같은것을 필터링한다
        return room -> room.getTier() == player.getTier();
    }
    default Predicate<ChatRoom> equalRank(Player player){return room -> room.getRank() == player.getRank();}
    default Predicate<ChatRoom> equalType(Player player){ // 게임 타입별(칼바람, 협곡)로 필터링
        return room -> room.getGameType() == player.getGameType();
    }
}
