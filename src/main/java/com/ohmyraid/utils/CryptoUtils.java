package com.ohmyraid.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CryptoUtils {

    public static String encryptPw(String plainPw){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        String encodedPw = encoder.encode(plainPw);
        log.debug("cryptedPw is {}", encodedPw);
        return encodedPw;
    }
}
