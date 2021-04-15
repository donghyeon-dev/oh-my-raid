package com.ohmyraid.vo.login;

import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.vo.character.CharacterVo;
import lombok.Data;

import java.util.List;

/**
 * 로그인 output Vo
 */
@Data
public class LoginOutpVo {

    private String email;

    private AccountEntity account;

    private List<CharacterEntity> characterList;

    private String accessToken;

}
