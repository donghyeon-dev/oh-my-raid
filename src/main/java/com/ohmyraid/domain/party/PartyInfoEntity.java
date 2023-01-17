package com.ohmyraid.domain.party;

import com.ohmyraid.domain.account.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "OMR_PARTY_INFO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id")
    private Long partyId;

    @Column(length = 50, nullable = false)
    private String instanceName;

    @Column(length = 6, nullable = false)
    private String difficulty;

    @Column(length = 5, nullable = false)
    private int requiredMembers;

    @Column(length = 5, nullable = false)
    private int times;

    @Column(length = 5, nullable = false)
    private String memberCapacity;

    @Column(length = 50, nullable = false)
    private String subject;

    @Column(length = 1000, nullable = false)
    private String contents;

    @Column(length = 10, nullable = true)
    private String slug;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity createAccountId;

    @Column(length = 10, nullable = false)
    private LocalDateTime startAt;

    @Column(length = 10, nullable = false)
    private LocalDateTime recruitUntil;


}
