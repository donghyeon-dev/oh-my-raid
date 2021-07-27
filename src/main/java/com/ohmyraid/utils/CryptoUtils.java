package com.ohmyraid.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CryptoUtils {

    public static String encryptPw(String plainPw){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        String encodedPw = encoder.encode(plainPw);
        log.debug("cryptedPw is {}", encodedPw);
        return encodedPw;
    }

    public static boolean isPwVerify(String plaiPw, String encryptedPw){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        return encoder.matches(plaiPw,encryptedPw);

    }

    /**
     * clientId와 clientSercret을 받아 AuthorizationHeader값을 반환
     * @return
     */
    public static String getAuthValue(){
        String result = "";
        String clientId = System.getenv("BLIZZARD_CLIENT_ID");
        String clientSecret = System.getenv("BLIZZARD_CLIENT_SECRET");

        if( !ObjectUtils.isEmpty(clientId) && !ObjectUtils.isEmpty(clientSecret) ){
            result = clientId+ ":" +clientSecret;
            result = "Basic "+ Base64Utils.encodeToString(result.getBytes(StandardCharsets.UTF_8));
            return result;
        } else {
            return result;
        }
    }
}
