package com.ohmyraid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SpecializationType {

    ARCANE("비전",62),
    FIRE("화염",63),
    FROST("냉기",64),
    HOLY_PALADIN("신성",65),
    PROTECTION_PALADIN("보호", 66),
    RETRIBUTION("징벌", 70),
    ARM("무기", 71),
    FURY("분노", 72),
    PROTECTION_WARRIOR("방어", 73),
    BALANCE("조화", 102),
    FERAL("야성", 103),
    GUARDIAN("수호", 104),
    RESTORATION_DRUID("회복", 105),
    BLOOD("혈기", 250),
    UNHOLY("부정", 252),
    BEAST_MATSERY("야수", 253),
    MARKSMANSHIP("사격", 254),
    SURVIVAL("생존", 255),
    DISCIPLINE("수양", 256),
    SHADOW("암흑", 258),
    ASSASSINATION("암살", 259),
    OUTLAW("무법", 260),
    SUBTLETY("잠행", 261),
    ELEMENTAL("정기", 262),
    ENHANCEMENT("고양", 263),
    RESTORATION_SHAMAN("복원", 264),
    AFFLICATION("고통", 265),
    DEMONOLOGY("악마", 266),
    DESTRUCTION("파괴", 267),
    BREWMASTER("양조", 268),
    WINDWALKER("풍운", 269),
    MISTWEAVER("운무", 270),
    HAVOC("파멸", 577),
    VENGEANCE("복수", 581),
    DEVASTATION("황폐", 1467),
    PRERSERVATION("보존", 1468),
    AUGMENTATION("증강", 1473),
    UNDER_LEVEL_10(null,0)
    ;

    private final String specializationName;
    private final int specializationId;

    public static SpecializationType getIdByName(String specializationName){
        return Arrays.stream(SpecializationType.values())
                .filter(targetType -> targetType.getSpecializationName().equals(specializationName))
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException("There is no described specialization."));
    }

    public static SpecializationType getNameById(int specializationId) {
        return Arrays.stream(SpecializationType.values())
                .filter(targetValue -> targetValue.getSpecializationId() == specializationId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described specialization."));
    }



}
