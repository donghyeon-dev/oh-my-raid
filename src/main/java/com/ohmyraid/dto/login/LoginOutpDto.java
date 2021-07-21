package com.ohmyraid.dto.login;

import com.ohmyraid.repository.character.CharacterLoginOpMapping;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 로그인 output Vo
 */
@Data
public class LoginOutpDto implements Serializable {
    private static final long serialVersionUID = -2925893922100802694L;

    private String email;

    private String nickname;

    private List<CharacterLoginOpMapping> characterList;

    private String accessToken;

}
