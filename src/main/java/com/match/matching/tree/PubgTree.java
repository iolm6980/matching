package com.match.matching.tree;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.node.GameOption;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class PubgTree implements GameTree{
    GameOption nodes;
    public PubgTree(){ // 배그 -> 랭크여부 -> 게임타입 -> 티어 순으로 트리를 만든다
        nodes = new GameOption<>(Game.PUBG);

        List<GameOption> isRankList = Arrays.asList(new GameOption<>(IsRank.RANK), new GameOption<>(IsRank.NORMAL));
        List<GameOption> gameTypeList = Arrays.asList(new GameOption<>(GameType.DUO), new GameOption<>(GameType.TRIO), new GameOption<>(GameType.TEAM),
                new GameOption<>(GameType.DUO), new GameOption<>(GameType.TEAM));
        List<GameOption> tierList = Arrays.asList(new GameOption<>(Tier.BRONZE), new GameOption<>(Tier.SILVER),
                new GameOption<>(Tier.GOLD), new GameOption<>(Tier.PLATINUM), new GameOption<>(Tier.DIAMOND),
                new GameOption<>(Tier.MASTER));

        for (GameOption rankOption : isRankList) {
            nodes.addChild(rankOption);
            nodes.setNextOption(IsRank.RANK);
        }

        for(GameOption rankOption: isRankList){
            for (GameOption typeOption : gameTypeList) {
                rankOption.addChild(typeOption);
                rankOption.setNextOption(GameType.DUO);
            }
        }

        for (GameOption typeOption : gameTypeList) {
            for(GameOption tierOption: tierList){
                typeOption.addChild(tierOption);
                typeOption.setNextOption(Tier.BRONZE);
            }
        }
    }

    @Override
    public ChatRoom enterRoom(Player player) {
        ChatRoom room = nodes.searchChatRoom(player,1);
        roomMap.put(room.getRoomId(), room);
        return room;
    }

    @Override
    public void removeChild(String roomId) {
        nodes.removeNode(roomId);
    }

    @Override
    public String provideName(String roomId){
        ChatRoom chatRoom = roomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        StringBuffer bf = new StringBuffer();
        if(!((nameList & (1<<0)) > 0)) {
            bf.append("플레이어1");
            nameList = nameList | (1<<0);
        }
        else if(!((nameList & (1<<1)) > 0)){
            bf.append("플레이어2");
            nameList = nameList | (1<<1);
        }
        else if(!((nameList & (1<<2)) > 0)){
            bf.append("플레이어3");
            nameList = nameList | (1<<2);
        }
        else if(!((nameList & (1<<3)) > 0)){
            bf.append("플레이어4");
            nameList = nameList | (1<<3);
        }
        chatRoom.setNameList(nameList);
        return bf.toString();
    }

    @Override
    public void collectName(String roomId, String name){
        ChatRoom chatRoom = roomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        if(name.equals("플레이어1")) nameList = nameList ^ (1<<0);
        else if(name.equals("플레이어2")) nameList = nameList ^ (1<<1);
        else if(name.equals("플레이어3")) nameList = nameList ^ (1<<2);
        else if(name.equals("플레이어4")) nameList = nameList ^ (1<<3);
        chatRoom.setNameList(nameList);
    }
}
