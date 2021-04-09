package com.ohmyraid.vo.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountVo {

    private long accountId;

    private String email;

    private String password;
}
