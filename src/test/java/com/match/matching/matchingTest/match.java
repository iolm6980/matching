package com.match.matching.matchingTest;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.game.LOL;
import com.match.matching.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import java.util.stream.IntStream;

@SpringBootTest
public class match {
    @Autowired
    private ChatService chatService;

    @Test
    public void test(){

    }

    @Test
    public void player(){
        LOL lol = new LOL();
        Player player = Player.builder()
                .game(Game.LOL)
                .gameType(GameType.ARAM)
                .tier(Tier.GOLD)
                .line(Line.AD)
                .build();
        ChatRoom chat = ChatRoom.builder()
                .game(Game.LOL)
                .gameType(GameType.ARAM)
                .tier(Tier.GOLD)
                .line(Line.AD)
                .build();
        lol.add(chat);
        ChatRoom chat1 = ChatRoom.builder()
                .game(Game.LOL)
                .gameType(GameType.ARAM)
                .tier(Tier.GOLD)
                .line(Line.AD)
                .build();
        lol.add(chat1);
        System.out.println(player.getGameType() + " / " + player.getTier() + " / " + player.getLine());
        IntStream.rangeClosed(1,100).forEach(
                i ->{
                    ChatRoom chatRoom = ChatRoom.builder()
                            .game(Game.LOL)
                            .gameType(GameType.getRandom())
                            .tier(Tier.getRandom())
                            .line(Line.getRandom())
                            .build();
                    lol.add(chatRoom);
                }
        );
        List<ChatRoom> list = lol.filteringRoom(player);
        System.out.println("-------------------------------------------------------------------------");
        lol.show();
        System.out.println("-------------------------------------------------------------------------");
        list.forEach(chatRoom -> System.out.println(chatRoom.getGameType() + " / " + chatRoom.getTier() + " / " + chatRoom.getLine()));
    }

}
