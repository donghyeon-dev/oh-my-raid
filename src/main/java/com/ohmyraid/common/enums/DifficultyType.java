package com.ohmyraid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum DifficultyType {

    LFR("공격대 찾기", "LFR", 1),
    NORMAL("일반", "NORMAL", 2),
    HEROIC("영웅", "HEROIC", 3),
    MYTHIC("신화","MYTHIC", 4);

    private final String difficultyName;

    private final String difficultyEnglishName;

    private final int difficultyId;

    public static DifficultyType getTypeByName(String difficultyName) {
        return Arrays.stream(DifficultyType.values())
                .filter(targetType -> targetType.getDifficultyName().equals(difficultyName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described difficulty."));
    }

    public static DifficultyType getTypeByEnglishname(String difficultyEnglishName){
        return Arrays.stream(DifficultyType.values())
                .filter(targetType -> targetType.getDifficultyEnglishName().equals(difficultyEnglishName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described difficulty."));
    }

    public static DifficultyType getTypeById(int difficultyId) {
        return Arrays.stream(DifficultyType.values())
                .filter(targetValue -> targetValue.getDifficultyId() == difficultyId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described difficulty."));
    }

}
