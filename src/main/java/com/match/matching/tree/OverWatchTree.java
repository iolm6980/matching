package com.match.matching.tree;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.node.GameOption;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class OverWatchTree implements GameTree{
    GameOption nodes;
    public OverWatchTree(){ // 오버워치 -> 랭크여부 -> 게임 타입 -> 티어 순으로 트리를 만든다
        nodes = new GameOption<>(Game.OVERWATCH);

        List<GameOption> isRankList = Arrays.asList(new GameOption<>(IsRank.RANK), new GameOption<>(IsRank.NORMAL));
        List<GameOption> gameTypeList = Arrays.asList(new GameOption<>(GameType.FIXEDROLE), new GameOption<>(GameType.FREEROLE));
        List<GameOption> tierList = Arrays.asList(new GameOption<>(Tier.BRONZE), new GameOption<>(Tier.SILVER),
                new GameOption<>(Tier.GOLD), new GameOption<>(Tier.PLATINUM),
                new GameOption<>(Tier.DIAMOND), new GameOption<>(Tier.MASTER),
                new GameOption<>(Tier.GRANDMASTER), new GameOption<>(Tier.CHALLENGER));

        for (GameOption rankOption : isRankList) {
            nodes.addChild(rankOption);
            nodes.setNextOption(IsRank.RANK);
        }

        for(GameOption rankOption: isRankList){
            for (GameOption typeOption : gameTypeList) {
                rankOption.addChild(typeOption);
                rankOption.setNextOption(GameType.FIXEDROLE);
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
        ChatRoom chatRoom = roomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        StringBuffer bf = new StringBuffer();
        if(!((nameList & (1<<0)) > 0)) {
            bf.append("트레이서");
            nameList = nameList | (1<<0);
        }
        else if(!((nameList & (1<<1)) > 0)){
            bf.append("키리코");
            nameList = nameList | (1<<1);
        }
        else if(!((nameList & (1<<2)) > 0)){
            bf.append("디바");
            nameList = nameList | (1<<2);
        }
        else if(!((nameList & (1<<3)) > 0)){
            bf.append("솔저");
            nameList = nameList | (1<<3);
        }
        else if(!((nameList & (1<<4)) > 0)){
            bf.append("리퍼");
            nameList = nameList | (1<<4);
        }
        else if(!((nameList & (1<<5)) > 0)){
            bf.append("래킹볼");
            nameList = nameList | (1<<5);
        }
        chatRoom.setNameList(nameList);
        return bf.toString();
    }

    @Override
    public void collectName(String roomId, String name){
        ChatRoom chatRoom = roomMap.get(roomId);
        int nameList = chatRoom.getNameList();
        if(name.equals("트레이서")) nameList = nameList ^ (1<<0);
        else if(name.equals("키리코")) nameList = nameList ^ (1<<1);
        else if(name.equals("디바")) nameList = nameList ^ (1<<2);
        else if(name.equals("솔저")) nameList = nameList ^ (1<<3);
        else if(name.equals("리퍼")) nameList = nameList ^ (1<<4);
        else if(name.equals("래킹볼")) nameList = nameList ^ (1<<5);
        chatRoom.setNameList(nameList);
    }

    private int lineToInt(Line line){// 라인을 숫자로 바꿔서 반환 0:탱커 1:딜러 2:힐러
        if(line == Line.TANKER) return (1 << 0);
        else if(line == Line.DEALER) return (1 << 1);
        else if(line == Line.HEALER) return (1 << 2);
        else return 0;
    }
}
