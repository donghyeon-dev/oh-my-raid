package com.ohmyraid.utils;

import com.ohmyraid.config.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class CryptoUtils {
    /* CryptoUtil은 Bean이 될 필요가 없지 않을까?
     BCryptPasswordEncoder를 static member varaiable로 선언하고
     메서드에서 갖다 쓰면 되지 않을까??
    */
    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    public static String encryptPw(String plainPw) {
        if(StringUtils.hasText(plainPw)) {
            String encodedPw = passwordEncoder.encode(plainPw);
            return encodedPw;
        } else {
            log.error("There is no password to encrypt.");
            throw new IllegalArgumentException("There is no string to encrypt.");
        }
    }

    public static boolean isPwVerify(String plaiPw, String encryptedPw) {
        return passwordEncoder.matches(plaiPw, encryptedPw);

    }

    /**
     * 환경변수로 저장됨 clientId와 clientSercret을 받아 AuthorizationHeader값을 반환
     *
     * @return
     */
    public static String getAuthValue() {
        String authorizationHeader;
        String clientId = Optional.of(System.getenv("BLIZZARD_CLIENT_ID")).orElse("cd5f2cc20f0e4be08e31ae9938e56b2d");
        String clientSecret = Optional.of(System.getenv("BLIZZARD_CLIENT_SECRET")).orElse("1penStTopokgRhM4fRtGwez2JUwyc7K2");

        if (StringUtils.hasText(clientId) && StringUtils.hasText(clientSecret)) {
            authorizationHeader = String.join(Constant.STRING_SPACE,
                    Constant.Auth.AUTHORIZATION_HEADER_BASIC,
                    Base64Utils.encodeToString(String.join(
                             ":"
                            ,clientId
                            ,clientSecret).getBytes(StandardCharsets.UTF_8)));
            return authorizationHeader;
        } else {
            log.error("There is no clientId or clientSecret");
            throw new IllegalArgumentException("There is no client parameter");
        }
    }
}
