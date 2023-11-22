package com.ohmyraid.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum RaceType {

    HUMAN("인간",1),
    ORC("오크",2),
    DRWARF("드워프",3),
    NIGHT_ELF("나이트 엘프",4),
    UNDEAD("언데드",5),
    TAUREN("타우렌",6),
    GNOME("노움",7),
    TROLL("트롤",8),
    GOBLIN("고블린",9),
    BLOOD_ELF("블러드 엘프",10),
    DRAENEI("드레나이",11),
    WORGEN("늑대인간",22),
    PANDAREN("판다렌",24),
    NIGHTBORNE("나이트본",27),
    HIGHMOUNTAIN_TAUREN("높은산 타우렌",28),
    VOID_ELF("공허엘프",29),
    LIGHTFORGED_DRAENEI("빛벼림 드레나이",30),
    ZANDALARI_TROLL("잔달라 트롤",31),
    KUL_TIRAN("쿨티란",32),
    DARK_IRON_DWARF("검은무쇠 드워프",34),
    VULPERA("불페라",35),
    MAGHAR_ORC("마그하르 오크",36),
    MECHAGNOME("기계노움",37),
    DRACTHYR("드랙티르",38);

    private final String raceName;

    private final int raceId;

    public static RaceType getIdByName(String raceName) {
        return Arrays.stream(RaceType.values())
                .filter(targetType -> targetType.getRaceName().equals(raceName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described race."));
    }

    public static RaceType getNameById(int raceId) {
        return Arrays.stream(RaceType.values())
                .filter(targetValue -> targetValue.getRaceId() == raceId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no described race."));
    }

}
