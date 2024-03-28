package com.match.matching.game;

import com.match.matching.dto.Player;
import com.match.matching.nodes.GameTree;
import com.match.matching.nodes.LoLTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TreeFactory {
    private final LoLTree loLTree;
    private final Overwatch overwatch;
    private final Pubg pubg;
    public GameTree getTree(Player player){
        if(player.getGame() == com.match.matching.Type.Game.LOL) return loLTree;
        else return null;
    }
}
