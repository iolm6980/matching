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
    private Tier tier;
    private GameType gameType;
    private Line line;
    private int lineList;
    private int maxPeople;
}
