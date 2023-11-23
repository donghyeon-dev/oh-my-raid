package com.ohmyraid.domain.raid;

import com.ohmyraid.domain.character.CharacterEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "OMR_RAID_DETAIL")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaidDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    CharacterEntity characterEntity;

    @Comment("확장팩명")
    @Column(length = 100, nullable = false)
    String expansionName;


    @Comment("난이도")
    @Column(length = 20, nullable = false)
    String difficulty;

    @Comment("공격대명")
    @Column(length = 100, nullable = false)
    String instanceName;

    @Comment("네임드명")
    @Column(length = 100, nullable = false)
    String bossName;

    @Comment("네임드 고유 ID")
    @Column(length = 10, nullable = false)
    long bossId;

    @Comment("총 킬수")
    @Column(length = 2, nullable = false)
    int completedCount;

    @Comment("최종수집 Timestamp")
    @Column(length = 12, nullable = false)
    LocalDateTime lastCrawledAt;

    @Comment("최종처치 Timestamp")
    @Column(length = 12, nullable = false)
    LocalDateTime lastKilledAt;

}
