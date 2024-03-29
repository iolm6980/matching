package com.match.matching.Type;

public enum IsRank {
    NORMAL, RANK;
    public static IsRank getRandom(){
        return values()[(int)(Math.random() * values().length)];
    }
}
