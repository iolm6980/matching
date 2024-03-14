package com.match.matching.Type;

public enum Line {
    MID, TOP, JUNGLE, SUPPORTER, AD, ALL;
    public static Line getRandom(){
        return values()[(int)(Math.random() * values().length)];
    }
}
