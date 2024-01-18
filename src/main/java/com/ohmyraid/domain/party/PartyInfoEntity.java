package com.ohmyraid.domain.party;

import com.ohmyraid.domain.user.UserEntity;
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
    @JoinColumn(name = "user_id")
    private UserEntity createUser;

    @Column(length = 10, nullable = false)
    private LocalDateTime startAt;

    @Column(length = 10, nullable = false)
    private LocalDateTime recruitUntil;


    public void update(String instanceName, String difficulty, int requiredMembers, int times, String memberCapacity,
                       String subject, String contents, String slug, LocalDateTime startAt, LocalDateTime recruitUntil) {
        this.instanceName = instanceName;
        this.difficulty = difficulty;
        this.requiredMembers = requiredMembers;
        this.times = times;
        this.memberCapacity = memberCapacity;
        this.subject = subject;
        this.contents = contents;
        this.slug = slug;
        this.startAt = startAt;
        this.recruitUntil = recruitUntil;
    }

}
