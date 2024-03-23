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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@SpringBootTest
public class match {
    @Autowired
    private ChatService chatService;

    @Test
    public void test(){
        String targetString = "/sub/chat/room/your_string_here";

        // 정규식 패턴
        String regexPattern = "/sub/chat/room/(\\w+)";

        // 패턴 객체 생성
        Pattern pattern = Pattern.compile(regexPattern);

        // 매칭 객체 생성
        Matcher matcher = pattern.matcher(targetString);

        // 매칭된 문자열 출력
        if (matcher.find()) {
            String extractedString = matcher.group(1);
            System.out.println(extractedString);
        } else {
            System.out.println("No match found.");
        }
    }

    @Test
    public void player(){
//        LOL lol = new LOL();
////        Player player = Player.builder()
////                .game(Game.LOL)
////                .gameType(GameType.NORMAL)
////                .tier(Tier.GOLD)
////                .line(Line.TOP)
////                .lineList(3)
////                .build();
////        ChatRoom chat = ChatRoom.builder()
////                .gameType(GameType.NORMAL)
////                .tier(Tier.GOLD)
////                .line(Line.MID)
////                .lineList(3)
////                .build();
////
////        ChatRoom chat1 = ChatRoom.builder()
////                .gameType(GameType.NORMAL)
////                .tier(Tier.GOLD)
////                .line(Line.MID)
////                .lineList(31)
////                .build();
////        lol.add(chat);
////        lol.add(chat1);
//
//       // System.out.println(player.getGameType() + " / " + player.getTier() + " / " + player.getLine());
//        IntStream.rangeClosed(1,300).forEach(
//                i ->{
//                    ChatRoom chatRoom = ChatRoom.builder()
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
