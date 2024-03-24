package com.match.matching.Type;

public enum GameType {
    ARAM, TFT, DUO, TRIO, TEAM,
    FIXEDROLE, FREEROLE,;

    public static GameType getRandom(){
        return values()[(int)(Math.random() * values().length)];
    }
}
