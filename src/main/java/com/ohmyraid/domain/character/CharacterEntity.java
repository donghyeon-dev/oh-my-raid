package com.ohmyraid.domain.character;

import com.ohmyraid.common.converter.*;
import com.ohmyraid.domain.account.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "OMR_CHARACTER")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long characterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    /**
     * 캐릭터 고유번호
     */
    @Column(length = 10, nullable = false, unique = true)
    private int characterSeNumber;

    @Column(length = 12, nullable = false)
    private String name;

    @Column(length = 2, nullable = false)
    private int level;

    @Convert(converter = PlaybleClassConverter.class)
    @Column(length = 12, nullable = false)
    private String playableClass;

    @Convert(converter = SpecializationConverter.class)
    @Column(length = 12, nullable = false)
    private String specialization;

    @Convert(converter = RaceConverter.class)
    @Column(length = 40, nullable = false)
    private String race;

    @Convert(converter = GenderConverter.class)
    @Column(length = 10, nullable = false)
    private String gender;

    @Convert(converter = FactionConverter.class)
    @Column(length = 10, nullable = false)
    private String faction;

    @Column(length = 10, nullable = false)
    private int equippedItemLevel;

    @Column(length = 10, nullable = false)
    private int averageItemLvel;

    @Column(length = 10, nullable = false)
    private String slug;

    @Column(length = 10, nullable = false)
    private LocalDateTime lastCrawledAt;

    @Column(length = 10, nullable = false)
    private String expansionOption;

    @Column(length = 10, nullable = false)
    private int expansionOptionLevel;

}
