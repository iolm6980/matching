package com.match.matching.Type;

public enum GameType {
    ARAM, TFT, DOURANK, FLEXRANK, NORMAL;
    public static GameType getRandom(){
        return values()[(int)(Math.random() * values().length)];
    }
}
