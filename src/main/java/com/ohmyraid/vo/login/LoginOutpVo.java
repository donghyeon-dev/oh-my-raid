package com.ohmyraid.vo.login;

import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.repository.character.CharacterLoginOpMapping;
import com.ohmyraid.vo.character.CharacterVo;
import lombok.Data;

import java.util.List;

/**
 * 로그인 output Vo
 */
@Data
public class LoginOutpVo {

    private String email;

    private String nickname;

    private List<CharacterLoginOpMapping> characterList;

    private String accessToken;

}
