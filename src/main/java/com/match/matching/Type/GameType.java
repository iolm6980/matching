package com.match.matching.Type;

public enum GameType {
    ARAM, TFT, DOURANK, TEAMRANK, NORMAL;
    public static GameType getRandom(){
        return values()[(int)(Math.random() * values().length)];
    }
}
