package com.ohmyraid.domain.character;

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
    @JoinColumn(name="account_id")
    private AccountEntity accountEntity;

    @Column(length = 12, nullable = false)
    private String name;

    @Column(length = 40, nullable = false)
    private String race;

    @Column(length = 12, nullable = false)
    private String classes;

    @Column(length = 25, nullable = false)
    private String activeSpecName;

    @Column(length = 10, nullable = false)
    private String activeSpecRole;

    @Column(length = 10, nullable = false)
    private String gender;

    @Column(length = 10, nullable = false)
    private String faction;

    @Column(length = 10, nullable = false)
    private int archievementPoints;

    @Column(length = 10, nullable = false)
    private String honorableKills;

    @Column(length = 10, nullable = false)
    private String region;

    @Column(length = 10, nullable = false)
    private String realm;

    @Column(length = 10, nullable = false)
    private LocalDateTime lastCrawledAt;

    // gear
    @Column(length = 10, nullable = false)
    private int itemLevelEquipped;

    //mythic_plus_scores_by_season:current
    @Column(length = 10, nullable = false)
    private int mythicPlusScoreBySeason;

}
