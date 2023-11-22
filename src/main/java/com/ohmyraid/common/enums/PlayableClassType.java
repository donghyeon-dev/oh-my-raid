package com.ohmyraid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum PlayableClassType {

    WARRIOR("전사",1),
    PALADIN("성기사", 2),
    HUNTER("사냥꾼",3),
    ROGUE("도적",4),
    PRIEST("사제",5),
    DEATH_KNIGHT("죽음의 기사",6),
    SHAMAN("주술사",7),
    MAGE("마법사",8),
    WARLOCK("흑마법사", 9),
    MONK("수도사", 10),
    DRUID("드루이드",11),
    DEMON_HUNTER("악마사냥꾼", 12),
    EVOKER("기원사", 13);


    private final String playableClassName;

    private final int playableClassId;

    public static PlayableClassType getIdByName(String playableClassName) {
        return Arrays.stream(PlayableClassType.values())
                .filter(targetType -> targetType.getPlayableClassName().equals(playableClassName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described playble class."));
    }

    public static PlayableClassType getNameById(int playableClassId) {
        return Arrays.stream(PlayableClassType.values())
                .filter(targetValue -> targetValue.getPlayableClassId() == playableClassId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described playble class."));
    }

}
