package com.match.matching.tree;

import com.match.matching.Type.*;
import com.match.matching.dto.ChatRoom;
import com.match.matching.dto.Player;
import com.match.matching.node.GameOption;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class LoLTree implements GameTree{
    GameOption nodes;
    public LoLTree(){ // 롤 -> 랭크여부 -> 게임타입 -> 티어 순으로 트리를 만든다
        nodes = new GameOption<>(Game.LOL);

        List<GameOption> isRankList = Arrays.asList(new GameOption<>(IsRank.RANK), new GameOption<>(IsRank.NORMAL));
        List<GameOption> gameTypeList = Arrays.asList(new GameOption<>(GameType.ARAM), new GameOption<>(GameType.TFT),
                new GameOption<>(GameType.DUO), new GameOption<>(GameType.TEAM));
        List<GameOption> tierList = Arrays.asList(new GameOption<>(Tier.BRONZE), new GameOption<>(Tier.SILVER),
                new GameOption<>(Tier.GOLD), new GameOption<>(Tier.PLATINUM),
                new GameOption<>(Tier.EMERALD), new GameOption<>(Tier.DIAMOND),
                new GameOption<>(Tier.MASTER), new GameOption<>(Tier.GRANDMASTER),
                new GameOption<>(Tier.CHALLENGER));

        for (GameOption rankOption : isRankList) {// 게임 옵션 및에 랭크 옵션을 연결
            nodes.addChild(rankOption);
            nodes.setNextOption(IsRank.RANK);
        }

        for(GameOption rankOption: isRankList){ // 랭크 옵션 및에 게임 타입 옵션을 연결 칼바람의 경우에는 랭크가 없으니 조건문으로 생략한다
            for (GameOption typeOption : gameTypeList) {
                if(rankOption.getOption() == IsRank.RANK && typeOption.getOption() == GameType.ARAM) continue;
                rankOption.addChild(typeOption);
                rankOption.setNextOption(GameType.TFT);
            }
        }

        for (GameOption typeOption : gameTypeList) { // 게임타입 옵션 및에 티어 연결
            for(GameOption tierOption: tierList){
                typeOption.addChild(tierOption);
                typeOption.setNextOption(Tier.BRONZE);
            }
        }
    }

    @Override
    public ChatRoom enterRoom(Player player) {
        ChatRoom room = nodes.searchChatRoom(player, lineToInt(player));
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
        else if(!((nameList & (1<<5)) > 0)){
            bf.append("블루");
            nameList = nameList | (1<<5);
        }
        else if(!((nameList & (1<<6)) > 0)){
            bf.append("레드");
            nameList = nameList | (1<<6);
        }
        else if(!((nameList & (1<<7)) > 0)){
            bf.append("드래곤");
            nameList = nameList | (1<<7);
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
        else if(name.equals("블루")) nameList = nameList ^ (1<<5);
        else if(name.equals("레드")) nameList = nameList ^ (1<<6);
        else if(name.equals("드래곤")) nameList = nameList ^ (1<<7);
        chatRoom.setNameList(nameList);
    }

    private int lineToInt(Player player){// 라인을 숫자로 바꿔서 반환 0:탑 1:정글 2:미드 3:원딜 4:서폿
        Line line = player.getLine();
        if(line == Line.TOP) return (1 << 0);
        else if(line == Line.JUNGLE) return (1 << 1);
        else if(line == Line.MID) return (1 << 2);
        else if(line == Line.AD) return (1 << 3);
        else if(line == Line.SUPPORTER) return (1 << 4);
        else return 0;
    }
}
