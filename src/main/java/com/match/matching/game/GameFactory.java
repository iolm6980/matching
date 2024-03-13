package com.match.matching.game;

import com.match.matching.dto.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameFactory {
    private final LOL lol;
    private final Overwatch overwatch;
    public Game getGame(Player player){
        if(player.getGame() == com.match.matching.Type.Game.LOL) return lol;
        else if(player.getGame() == com.match.matching.Type.Game.OVERWATCH) return overwatch;
        else return null;
    }
}
