package com.match.matching.matchingTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class match {
    @Test
    public void player(){
        System.out.println("player test....................");
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


}
