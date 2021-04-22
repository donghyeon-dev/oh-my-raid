package com.ohmyraid.domain.party;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "OMR_PARTY")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long partyId;

    @Column(length = 50, nullable = false)
    private String subject;

    @Column(length = 6, nullable = false)
    private String difficulty;

    @Column(length = 5, nullable = false)
    private int requiredMember;

    @Column(length = 5, nullable = false)
    private int times;

    @Column(length = 5, nullable = false)
    private String memberCapacity;

    @Column(length = 100, nullable = false)
    private String contents;

    @Column(length = 50, nullable = false)
    private String creater;


}
