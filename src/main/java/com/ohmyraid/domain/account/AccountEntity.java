package com.ohmyraid.domain.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "OMR_ACCOUNT")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement
    @Column(name="account_id")
    private Long accountId;

    @Column(length = 25, nullable = false, unique = true)
    private String email;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 25, nullable = false, unique = true)
    private String nickname;

}





