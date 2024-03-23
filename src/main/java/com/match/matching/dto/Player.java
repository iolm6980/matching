package com.match.matching.dto;

import com.match.matching.Type.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Game game;
    private GameType gameType;
    private Tier tier;
    private Line line;
    private int lineList;
    private int maxPeople;
}
