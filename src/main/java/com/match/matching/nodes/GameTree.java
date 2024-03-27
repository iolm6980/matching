package com.match.matching.nodes;

import com.match.matching.Type.*;

import java.util.ArrayList;
import java.util.List;
public class GameTree {
    GameOption game;

    public GameTree(){
        game = new GameOption(Game.LOL);

        List<GameOption> isRankList = new ArrayList<>();
        isRankList.add(new GameOption(IsRank.RANK));
        isRankList.add(new GameOption(IsRank.NORMAL));

        List<GameOption> gameTypeList = new ArrayList<>();
        gameTypeList.add(new GameOption<>(GameType.ARAM));
        gameTypeList.add(new GameOption<>(GameType.TFT));
        gameTypeList.add(new GameOption<>(GameType.DUO));
        gameTypeList.add(new GameOption<>(GameType.TEAM));

        List<GameOption> lineList = new ArrayList<>();
        lineList.add(new GameOption(Line.AD));
        lineList.add(new GameOption(Line.SUPPORTER));
        lineList.add(new GameOption(Line.TOP));
        lineList.add(new GameOption(Line.JUNGLE));
        lineList.add(new GameOption(Line.MID));

        List<GameOption> tierList = new ArrayList<>();
        tierList.add(new GameOption(Tier.BRONZE));
        tierList.add(new GameOption(Tier.SILVER));
        tierList.add(new GameOption(Tier.GOLD));
        tierList.add(new GameOption(Tier.PLATINUM));
        tierList.add(new GameOption(Tier.EMERALD));
        tierList.add(new GameOption(Tier.DIAMOND));
        tierList.add(new GameOption(Tier.MASTER));
        tierList.add(new GameOption(Tier.GRANDMASTER));
        tierList.add(new GameOption(Tier.CHALLENGER));

        for (GameOption rankOption : isRankList) {
            game.addChild(rankOption);
        }

        for(GameOption rankOption: isRankList){
            for (GameOption typeOption : gameTypeList) {
                if(rankOption.option == IsRank.RANK && typeOption.option == GameType.ARAM) continue;
                rankOption.addChild(typeOption);
            }
        }

        for (GameOption typeOption : gameTypeList) {
            for(GameOption lineOption: lineList){
                if(typeOption.option == GameType.TFT || typeOption.option == GameType.ARAM) continue;
                typeOption.addChild(lineOption);
            }
        }

        for(GameOption lineOption: lineList){
            for(GameOption tierOption: tierList){
                lineOption.addChild(tierOption);
            }
        }

    }
}
