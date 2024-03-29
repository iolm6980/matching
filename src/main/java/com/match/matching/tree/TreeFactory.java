package com.match.matching.tree;

import com.match.matching.Type.Game;
import com.match.matching.dto.Player;
import com.match.matching.game.Overwatch;
import com.match.matching.game.Pubg;
import com.match.matching.tree.GameTree;
import com.match.matching.tree.LoLTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TreeFactory {
    private final LoLTree loLTree;
    private final OverWatchTree overWatchTree;
    private final PubgTree pubgTree;
    public GameTree getTree(Player player){
        if(player.getGame() == Game.LOL) return loLTree;
        else if(player.getGame() == Game.OVERWATCH) return overWatchTree;
        else if(player.getGame() == Game.PUBG) return pubgTree;
        else return null;
    }
}
