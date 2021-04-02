package com.ohmyraid.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtils {

    public static String encryptPw(String plainPw){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
        String encodedPw = encoder.encode(plainPw);
        return encodedPw;
    }
}
