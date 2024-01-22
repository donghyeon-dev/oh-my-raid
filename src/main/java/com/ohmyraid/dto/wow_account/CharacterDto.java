package com.ohmyraid.dto.wow_account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * CharacterEntity 의 DT 를 도와주는 DTO 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterDto {

    private long characterId;

    private long userId;

    private int characterSeNumber;

    private String name;

    private int level;

    private String playableClass;

    private String specialization;

    private String race;

    private String gender;

    private int equippedItemLevel;

    private int averageItemLvel;

    private String slug;

    private String faction;

    private LocalDateTime lastCrawledAt;


    @QueryProjection
    public CharacterDto(long characterId, String name, String slug) {
        this.characterId = characterId;
        this.name = name;
        this.slug = slug;
    }

    @QueryProjection
    public CharacterDto(int characterSeNumber, String name, int level,
                        String playableClass, String specialization, String race, String gender, int equippedItemLevel,
                        int averageItemLvel, String slug, String faction, LocalDateTime lastCrawledAt) {
        this.characterSeNumber = characterSeNumber;
        this.name = name;
        this.level = level;
        this.playableClass = playableClass;
        this.specialization = specialization;
        this.race = race;
        this.gender = gender;
        this.equippedItemLevel = equippedItemLevel;
        this.averageItemLvel = averageItemLvel;
        this.slug = slug;
        this.faction = faction;
        this.lastCrawledAt = lastCrawledAt;
    }
}
