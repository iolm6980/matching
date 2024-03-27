package com.match.matching.nodes;

import com.match.matching.Type.Game;

import java.util.HashMap;

public class RootNode {
    HashMap<Game, GameNode> game = new HashMap();

    RootNode(){
        game.put(Game.LOL, new GameNode(Game.LOL));
        game.put(Game.OVERWATCH, new GameNode(Game.LOL));
        game.put(Game.PUBG, new GameNode(Game.LOL));
    }
}
