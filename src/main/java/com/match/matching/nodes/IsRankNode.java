package com.match.matching.nodes;

import com.match.matching.Type.Game;
import com.match.matching.Type.GameType;
import com.match.matching.Type.IsRank;

import java.util.HashMap;

public class IsRankNode {
    IsRank isRank;
    HashMap<GameType, GameTypeNode> gameType = new HashMap();
    IsRankNode(IsRank isRank){

    }
}
