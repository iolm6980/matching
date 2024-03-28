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
    GameOption gameOptions;
    public LoLTree(){ // 롤 -> 랭크여부 -> 라인 -> 티어 순으로 트리를 만든다
        gameOptions = new GameOption<>(Game.LOL);

        List<GameOption> isRankList = Arrays.asList(new GameOption<>(IsRank.RANK), new GameOption<>(IsRank.NORMAL));
        List<GameOption> gameTypeList = Arrays.asList(new GameOption<>(GameType.ARAM), new GameOption<>(GameType.TFT),
                new GameOption<>(GameType.DUO), new GameOption<>(GameType.TEAM));
        List<GameOption> lineList = Arrays.asList(new GameOption<>(Line.AD), new GameOption<>(Line.SUPPORTER),
                new GameOption<>(Line.TOP), new GameOption<>(Line.JUNGLE),
                new GameOption<>(Line.MID));
        List<GameOption> tierList = Arrays.asList(new GameOption<>(Tier.BRONZE), new GameOption<>(Tier.SILVER),
                new GameOption<>(Tier.GOLD), new GameOption<>(Tier.PLATINUM),
                new GameOption<>(Tier.EMERALD), new GameOption<>(Tier.DIAMOND),
                new GameOption<>(Tier.MASTER), new GameOption<>(Tier.GRANDMASTER),
                new GameOption<>(Tier.CHALLENGER));

        for (GameOption rankOption : isRankList) {
            gameOptions.addChild(rankOption);
            gameOptions.nextOption = IsRank.RANK;
        }

        for(GameOption rankOption: isRankList){
            for (GameOption typeOption : gameTypeList) {
                if(rankOption.option == IsRank.RANK && typeOption.option == GameType.ARAM) continue;
                rankOption.addChild(typeOption);
                rankOption.nextOption = GameType.TFT;
            }
        }

        for (GameOption typeOption : gameTypeList) {
            for(GameOption lineOption: lineList){
                if(typeOption.option == GameType.TFT || typeOption.option == GameType.ARAM) continue;
                typeOption.addChild(lineOption);
                typeOption.nextOption = Line.ALL;
            }
        }

        for(GameOption lineOption: lineList){
            for(GameOption tierOption: tierList){
                lineOption.addChild(tierOption);
                lineOption.nextOption = Tier.BRONZE;
            }
        }
    }

    @Override
    public ChatRoom getRoom(Player player) {
        return gameOptions.searchChatRoom(player);
    }
}
