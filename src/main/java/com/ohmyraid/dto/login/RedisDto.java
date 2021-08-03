package com.ohmyraid.dto.login;

import com.ohmyraid.repository.character.CharacterLoginOpMapping;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Redis Session Vo
 * 로그인 결과 혹은 Redis의 밸류로 저장되는 사용자의 정보
 */
@Data
public class RedisDto implements Serializable {
    private static final long serialVersionUID = -2925893922100802694L;

    private String email;

    private String nickname;

    private long accountId;

    private String accessToken;

    private String bzAccessToken;

}
