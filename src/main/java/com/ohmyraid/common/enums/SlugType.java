package com.ohmyraid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SlugType {
    BURNING_LEGION("불타는 군단", "burning-legion", 201),
    AZSHARA("아즈샤라", "azshara", 205),
    DALARAN("달라란", "dalaran", 207),
    DUROTAN("듀로탄", "durotan", 210),
    NORGANNON("노르간논", "norgannon", 211),
    GARONA("가로나", "garona", 212),
    WINDRUNNER("윈드러너", "windrunner", 214),
    GULDAN("굴단", "guldan", 215),
    ALEXSTRASZA("알렉스트라자", "alexstrasza", 258),
    MALFURION("말퓨리온", "malfurion", 264),
    HELLSCREAM("헬스크림", "hellscream", 293),
    WILDHAMMER("와일드해머", "wildhammer", 2079),
    REXXAR("렉사르", "rexxar", 2106),
    HYJAL("하이잘", "hyjal", 2107),
    DEATHWING("데스윙", "deathwing", 2108),
    CENARIUS("세나리우스", "cenarius", 2110),
    STORMRAGE("스톰레이지", "stormrage", 2111),
    ZULJIN("줄진", "zuljin", 2116);

    private final String slugName;

    private final String slugEnglishName;

    private final int slugId;

    public static SlugType getTypeByName(String slugName) {
        return Arrays.stream(SlugType.values())
                .filter(targetType -> targetType.getSlugName().equals(slugName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described slug."));
    }

    public static String getSlugEnglishNameByKorName(String slugName){
        return Arrays.stream(SlugType.values())
                .filter(targetType -> targetType.getSlugName().equals(slugName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described slug.")).getSlugEnglishName();
    }

    public static SlugType getTypeBySlugEnglishname(String slugEnglishName){
        return Arrays.stream(SlugType.values())
                .filter(targetType -> targetType.getSlugEnglishName().equals(slugEnglishName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described slug."));
    }

    public static SlugType getTypeById(int slugId) {
        return Arrays.stream(SlugType.values())
                .filter(targetValue -> targetValue.getSlugId() == slugId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described slug."));
    }

}
