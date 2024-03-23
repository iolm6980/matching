package com.match.matching.Type;

public enum Tier {
    BRONZE, SILVER, GOLD, PLATINUM, EMERALD, DIAMOND, MASTER, GRANDMASTER, CHALLENGER;
    public static Tier getRandom(){
        return values()[(int)(Math.random() * values().length)];
    }
}
