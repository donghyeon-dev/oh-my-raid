//package com.ohmyraid.domain.character;
//
//import com.ohmyraid.domain.account.AccountEntity;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity(name = "OMR_CHARACTER_RAID_INF")
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class CharacterRaidInfEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "raid_id")
//    private Long raidId;
//
//    @ManyToOne
//    @JoinColumn(name="character_id")
//    private CharacterEntity characterEntity;
//
//    // 레이드명 ex )Castle-nathria
//    @Column(length = 30, nullable = false)
//    private String raidTier;
//
//    // 요약
//    @Column(length = 10, nullable = false)
//    private String summary;
//
//    // 총 보스
//    @Column(length = 10, nullable = false)
//    private int totalBosses;
//
//    // 노말 레이드 킬
//    @Column(length = 10, nullable = false)
//    private int normalBossesKilled;
//
//    // 영웅 레이드 킬
//    @Column(length = 10, nullable = false)
//    private int heroicBossesKilled;
//
//    // 신화 레이드 킬
//    @Column(length = 10, nullable = false)
//    private int mythicBossesKilled;
//}
