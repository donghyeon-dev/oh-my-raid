package com.ohmyraid.dto.login;

import com.ohmyraid.repository.character.CharacterLoginOpMapping;
import lombok.Data;

import java.util.List;

/**
 * 로그인 output Vo
 */
@Data
public class LoginOutpDto {

    private String email;

    private String nickname;

    private List<CharacterLoginOpMapping> characterList;

    private String accessToken;

}
