package com.ohmyraid.dto.character;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * BlahBlah Entity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CharacterRaidInfoDto {

    @ApiModelProperty(value = "상세테이블 ID값", name = "deatilId")
    private Long detailId;

    @ApiModelProperty(value = "캐릭터명", name = "characterName")
    private String characterName;

    @ApiModelProperty(value = "확장팩명", name = "expansionName")
    private String expansionName;

    @ApiModelProperty(value = "공격대명", name = "instanceName")
    private String instanceName;

    @ApiModelProperty(value = "난이도", name = "difficulty")
    private String difficulty;

    @ApiModelProperty(value = "네임드명", name = "bossName")
    private String bossName;

    @ApiModelProperty(value = "처치횟수", name = "completedCount")
    private int completedCount;

    @ApiModelProperty(value = "최근처치날짜", name = "lastKilledAt")
    private LocalDateTime lastKilledAt;

    @ApiModelProperty(value = " 최근수집날짜", name = "lastCrawledAt")
    private LocalDateTime lastCrawledAt;

    @QueryProjection
    public CharacterRaidInfoDto(Long detailId, String characterName, String expansionName, String instanceName,
                                String difficulty, String bossName, int completedCount, LocalDateTime lastKilledAt, LocalDateTime lastCrawledAt) {
        this.detailId = detailId;
        this.characterName = characterName;
        this.expansionName = expansionName;
        this.instanceName = instanceName;
        this.difficulty = difficulty;
        this.bossName = bossName;
        this.completedCount = completedCount;
        this.lastKilledAt = lastKilledAt;
        this.lastCrawledAt = lastCrawledAt;
    }
}
