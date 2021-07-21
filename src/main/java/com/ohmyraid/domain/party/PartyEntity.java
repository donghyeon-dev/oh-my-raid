package com.ohmyraid.domain.party;

import com.ohmyraid.domain.account.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private int requiredMembers;

    @Column(length = 5, nullable = false)
    private int times;

    @Column(length = 5, nullable = false)
    private String memberCapacity;

    @Column(length = 1000, nullable = false)
    private String contents;

    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity creater;

    @Column(length = 10,nullable = false)
    private LocalDateTime  startAt;

    @Column(length = 10,nullable =   false)
    private LocalDateTime  recruitUntil;


}
