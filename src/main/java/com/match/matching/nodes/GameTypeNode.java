package com.match.matching.nodes;

import com.match.matching.Type.GameType;
import com.match.matching.Type.IsRank;

import java.util.HashMap;

public class GameTypeNode {
    GameType gameType;
    HashMap<IsRank, IsRankNode> isRankMap = new HashMap<>();
    GameTypeNode(GameType gameType){
        this.gameType = gameType;

    }
}
