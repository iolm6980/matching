package com.match.matching.matchingTest;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.game.LOL;
import com.match.matching.nodes.GameOption;
import com.match.matching.nodes.GameTree;
import com.match.matching.nodes.LoLTree;
import com.match.matching.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@SpringBootTest
public class match {
    @Autowired
    ChatService chatService;
    @Test
    public void player(){
        StopWatch stopWatch = new StopWatch();

        Player enter = Player.builder()
                .game(Game.LOL)
                .isRank(IsRank.RANK)
                .gameType(GameType.TEAM)
                .tier(Tier.MASTER)
                .line(Line.TOP)
                .lineList(31)
                .build();
        chatService.enterPlayer(enter);

        for(int i=0; i<1000000; i++){
            ChatRoom chatRoom = ChatRoom.builder()
                    .roomId(UUID.randomUUID().toString())
                    .game(Game.LOL)
                    .isRank(IsRank.getRandom())
                    .gameType(GameType.getRandom())
                    .tier(Tier.getRandom())
                    .line(Line.getRandom())
                    .lineList((int) (Math.random() * 31))
                    .build();
            chatService.add(chatRoom);
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .game(Game.LOL)
                .isRank(IsRank.RANK)
                .gameType(GameType.TEAM)
                .tier(Tier.GOLD)
                .line(Line.MID)
                .lineList(31)
                .build();
        chatService.add(chatRoom);

        Player player = Player.builder()
                .game(Game.LOL)
                .isRank(IsRank.RANK)
                .gameType(GameType.TEAM)
                .tier(Tier.GOLD)
                .line(Line.TOP)
                .lineList(31)
                .build();

        stopWatch.start();
        String roomId = chatService.enterPlayer(player);
        System.out.println("roomId " + roomId);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
        //System.out.println(chatRoom);
//        LOL lol = new LOL();
//        Player player = Player.builder()
//                .game(Game.LOL)
//                .rank(Rank.RANK)
//                .gameType(GameType.TEAM)
//                .tier(Tier.GOLD)
//                .line(Line.TOP)
//                .lineList(31)
//                .build();
//        ChatRoom chat = ChatRoom.builder()
//                .game(Game.LOL)
//                .gameType(GameType.TEAM)
//                .rank(Rank.RANK)
//                .tier(Tier.GOLD)
//                .line(Line.MID)
//                .lineList(31)
//                .build();
//
//        ChatRoom chat1 = ChatRoom.builder()
//                .game(Game.LOL)
//                .gameType(GameType.TEAM)
//                .tier(Tier.GOLD)
//                .rank(Rank.RANK)
//                .line(Line.MID)
//                .lineList(31)
//                .build();
//        lol.add(chat);
//        lol.add(chat1);
//
//        System.out.println(player.getGameType() + " / " + player.getTier() + " / " + player.getLine());
//        IntStream.rangeClosed(1,300).forEach(
//                i ->{
//                    ChatRoom chatRoom = ChatRoom.builder()
//                            .roomId(UUID.randomUUID().toString())
//                            .game(Game.LOL)
//                            .rank(Rank.getRandom())
//                            .gameType(GameType.getRandom())
//                            .tier(Tier.getRandom())
//                            .line(Line.getRandom())
//                            .lineList((int) (Math.random() * 31))
//                            .build();
//                    lol.add(chatRoom);
//                }
//        );
//        List<ChatRoom> list = lol.getFilteringRoom(player);
//        System.out.println("-------------------------------------------------------------------------");
//        lol.show();
//        System.out.println("-------------------------------------------------------------------------");
//        list.forEach(chatRoom -> System.out.println( chatRoom.getGameType() + " / " + chatRoom.getTier() + " / " + chatRoom.getLine() + " / "
//                + Integer.toBinaryString(chatRoom.getLineList())));
    }

    @Test
    public void treeTest(){
    }


}
