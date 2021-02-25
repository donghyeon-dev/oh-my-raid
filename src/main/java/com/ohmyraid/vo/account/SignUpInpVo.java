package com.ohmyraid.vo.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class SignUpInpVo {

    private String email;
    private String password;
}
