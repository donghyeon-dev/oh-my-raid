package com.ohmyraid.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {

    private long accountId;

    private String email;

    private String password;
}
