package com.ohmyraid.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

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
        String result = "";
        String clientId = System.getenv("BLIZZARD_CLIENT_ID");
        String clientSecret = System.getenv("BLIZZARD_CLIENT_SECRET");

        if (StringUtils.hasText(clientId) && StringUtils.hasText(clientSecret)) {
            result = clientId + ":" + clientSecret;
            result = "Basic " + Base64Utils.encodeToString(result.getBytes(StandardCharsets.UTF_8));
            return result;
        } else {
            log.error("There is no clientId or clientSecret");
            throw new IllegalArgumentException("There is no client parameter");
        }
    }
}
