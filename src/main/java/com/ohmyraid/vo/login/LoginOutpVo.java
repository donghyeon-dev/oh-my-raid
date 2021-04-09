package com.ohmyraid.vo.login;

import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.vo.character.CharacterVo;
import lombok.Data;

/**
 * 로그인 output Vo
 */
@Data
public class LoginOutpVo {

    private String email;

    private AccountEntity account;

    private String accessToken;

}
