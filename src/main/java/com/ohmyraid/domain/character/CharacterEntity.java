package com.ohmyraid.domain.character;

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
    private Long id;

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

}
