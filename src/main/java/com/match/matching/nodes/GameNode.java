package com.match.matching.nodes;

import com.match.matching.Type.Game;
import com.match.matching.Type.GameType;
import com.match.matching.Type.IsRank;

import java.util.HashMap;

public class GameNode {
    Game game;
    HashMap<GameType, GameTypeNode> gameTypeMap = new HashMap();
    GameNode(Game game){
        this.game = game;
        if(game == Game.LOL){
            gameTypeMap.put(GameType.ARAM, new GameTypeNode(GameType.ARAM));
            gameTypeMap.put(GameType.TFT, new GameTypeNode(GameType.TFT));
            gameTypeMap.put(GameType.DUO, new GameTypeNode(GameType.DUO));
            gameTypeMap.put(GameType.TEAM, new GameTypeNode(GameType.TEAM));
        }else if(game == Game.OVERWATCH){
            gameTypeMap.put(GameType.FIXEDROLE, new GameTypeNode(GameType.FIXEDROLE));
            gameTypeMap.put(GameType.FREEROLE, new GameTypeNode(GameType.FREEROLE));
        }
    }
}
