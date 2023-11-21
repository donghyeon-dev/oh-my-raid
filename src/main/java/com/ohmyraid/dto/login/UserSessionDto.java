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
    /**
     * Authorization_Code 방식으로 받아온 토큰(getAccountProfileSummary()에는 꼭 사용됨)
     * 즉 최초에 한번 캐릭터를 Fetch 하는 시점에는 위 flow로 토큰을 받아서 계정의 캐릭터 정보를 다 가져와야함
     */
    private String blizzardAccessToken;

}
