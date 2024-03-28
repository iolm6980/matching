package com.match.matching.nodes;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class LoLTree implements GameTree{
    GameOption nodes;
    public LoLTree(){ // 롤 -> 랭크여부 -> 티어 순으로 트리를 만든다
        nodes = new GameOption<>(Game.LOL);

        List<GameOption> isRankList = Arrays.asList(new GameOption<>(IsRank.RANK), new GameOption<>(IsRank.NORMAL));
        List<GameOption> gameTypeList = Arrays.asList(new GameOption<>(GameType.ARAM), new GameOption<>(GameType.TFT),
                new GameOption<>(GameType.DUO), new GameOption<>(GameType.TEAM));
        List<GameOption> tierList = Arrays.asList(new GameOption<>(Tier.BRONZE), new GameOption<>(Tier.SILVER),
                new GameOption<>(Tier.GOLD), new GameOption<>(Tier.PLATINUM),
                new GameOption<>(Tier.EMERALD), new GameOption<>(Tier.DIAMOND),
                new GameOption<>(Tier.MASTER), new GameOption<>(Tier.GRANDMASTER),
                new GameOption<>(Tier.CHALLENGER));

        for (GameOption rankOption : isRankList) {
            nodes.addChild(rankOption);
            nodes.nextOption = IsRank.RANK;
        }

        for(GameOption rankOption: isRankList){
            for (GameOption typeOption : gameTypeList) {
                if(rankOption.option == IsRank.RANK && typeOption.option == GameType.ARAM) continue;
                rankOption.addChild(typeOption);
                rankOption.nextOption = GameType.TFT;
            }
        }

        for (GameOption typeOption : gameTypeList) {
            for(GameOption tierOption: tierList){
                if(typeOption.option == GameType.TFT || typeOption.option == GameType.ARAM) continue;
                typeOption.addChild(tierOption);
                typeOption.nextOption = Tier.BRONZE;
            }
        }
    }

    @Override
    public ChatRoom enterRoom(Player player) {
        ChatRoom room = nodes.searchChatRoom(player, lineToInt(player.getLine()));
        roomMap.put(room.getRoomId(), room);
        return room;
    }

    @Override
    public void removeChild(String roomId) {
        nodes.removeNode(roomId);
    }

    @Override
    public String provideName(String roomId){
        System.out.println("lol 이름 가져오기 ");
        ChatRoom chatRoom = roomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        StringBuffer bf = new StringBuffer();
        if(!((nameList & (1<<0)) > 0)) {
            bf.append("칼날부리");
            nameList = nameList | (1<<0);
        }
        else if(!((nameList & (1<<1)) > 0)){
            bf.append("돌거북");
            nameList = nameList | (1<<1);
        }
        else if(!((nameList & (1<<2)) > 0)){
            bf.append("심술두꺼비");
            nameList = nameList | (1<<2);
        }
        else if(!((nameList & (1<<3)) > 0)){
            bf.append("바위게");
            nameList = nameList | (1<<3);
        }
        else if(!((nameList & (1<<4)) > 0)){
            bf.append("어스름늑대");
            nameList = nameList | (1<<4);
        }
        chatRoom.setNameList(nameList);
        return bf.toString();
    }

    @Override
    public void collectName(String roomId, String name){
        ChatRoom chatRoom = roomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        if(name.equals("칼날부리")) nameList = nameList ^ (1<<0);
        else if(name.equals("돌거북")) nameList = nameList ^ (1<<1);
        else if(name.equals("심술두꺼비")) nameList = nameList ^ (1<<2);
        else if(name.equals("바위게")) nameList = nameList ^ (1<<3);
        else if(name.equals("어스름늑대")) nameList = nameList ^ (1<<4);
        chatRoom.setNameList(nameList);
        System.out.println(name + " 회수함.....");
    }

    private int lineToInt(Line line){// 라인을 숫자로 바꿔서 반환 0:탑 1:정글 2:미드 3:원딜 4:서폿
        if(line == Line.TOP) return (1 << 0);
        else if(line == Line.JUNGLE) return (1 << 1);
        else if(line == Line.MID) return (1 << 2);
        else if(line == Line.AD) return (1 << 3);
        else if(line == Line.SUPPORTER) return (1 << 4);
        else return 0;
    }
}
