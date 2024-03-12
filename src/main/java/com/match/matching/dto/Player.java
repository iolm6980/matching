package com.match.matching.dto;

import com.match.matching.Type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Game game;
    private Tier tier;
    private GameType gameType;
    private int people;
    private Line line;
}
