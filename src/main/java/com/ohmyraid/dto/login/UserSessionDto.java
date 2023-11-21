package com.ohmyraid.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Redis Session Vo
 * 로그인 결과 혹은 Redis의 밸류로 저장되는 사용자의 정보
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionDto implements Serializable {
    private static final long serialVersionUID = -2925893922100802694L;

    private String email;

    private String nickname;

    private long accountId;

    private String accessToken;

}
