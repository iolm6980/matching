package com.match.matching.Type;

public enum Rank {
    NORMAL, RANK;
    public static Rank getRandom(){
        return values()[(int)(Math.random() * values().length)];
    }
}
