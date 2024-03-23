package com.match.matching.Type;

public enum GameType {
    ARAM, TFT, DOU, TRIO,TEAM, // 롤
    FIXEDROLE, FREEROLE,; // 오버워치

    public static GameType getRandom(){
        return values()[(int)(Math.random() * values().length)];
    }
}
