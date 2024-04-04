package com.match.matching.Type;

import com.match.matching.dto.Player;

public enum Tier {
    BRONZE, SILVER, GOLD, PLATINUM, EMERALD, DIAMOND, MASTER, GRANDMASTER, CHALLENGER;
    public static Tier getRandom(){
        return values()[(int)(Math.random() * values().length)];
    }
    public static Tier UpTier(Tier tier){
        return values()[getTierNum(tier) + 1];
    }

    public static int getTierNum(Tier tier){
        return tier.ordinal();
    }

    public static Tier DownTier(Tier tier){
        return values()[getTierNum(tier) - 1];
    }
}
