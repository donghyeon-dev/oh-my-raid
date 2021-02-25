package com.ohmyraid.domain.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement
    private long id;

    @Column(length = 25, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String password;


}
