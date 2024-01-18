package com.ohmyraid.dto.wow_account;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterProfileSummary {

    @ApiModelProperty(value = "캐릭터 고유 값(=characterSeNumber)", name = "id")
    private int id;

    @ApiModelProperty(value = "캐릭터 이름", name = "name")
    private String name;

    @ApiModelProperty(value = "성별", name = "gender")
    private Gender gender;

    @ApiModelProperty(value = "진영", name = "faction")
    private Faction faction;

    @ApiModelProperty(value = "종족", name = "race")
    private PlayableRace race;

    @ApiModelProperty(value = "직업", name = "characterClass")
    private PlayableClass characterClass;

    @ApiModelProperty(value = "캐릭터 전문화", name = "activeSpec")
    private ActiveSpec activeSpec;

    @ApiModelProperty(value = "서버", name = "realm")
    private Realm realm;

    @ApiModelProperty(value = "캐릭터 레벨", name = "level")
    private int level;

    @ApiModelProperty(value = "최종 로그인 타임스탬프(EPOCH)", name = "lastLoginTimeStamp")
    private long lastLoginTimestamp;

    @ApiModelProperty(value = "보유 아이템 레벨 평균", name = "averageItemLevel")
    private int averageItemLevel;

    @ApiModelProperty(value = "착용 아이템 레벨 평균", name = "equippedItemLevel")
    private int equippedItemLevel;

    @ApiModelProperty(value = "영예 진행상황(어둠땅 영예)", name = "covenantProgress",notes = "미사용 컬럼인데 저장은 하는중..")
    private CovenantProgress covenantProgress;
}

