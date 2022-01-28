package com.ohmyraid.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CryptoUtilsTest {

    @Autowired
    CryptoUtils cryptoUtils;

    @Test
    void 통과해야하는_getAuthValue() {
        String result = CryptoUtils.getAuthValue();
        assertEquals(result, "Basic Y2Q1ZjJjYzIwZjBlNGJlMDhlMzFhZTk5MzhlNTZiMmQ6MXBlblN0VG9wb2tnUmhNNGZSdEd3ZXoySlV3eWM3SzI=");
    }

    @Test
    void encryptPwWithValidation() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        String plainPw = "qwe123";
        String encryptedPw = encoder.encode(plainPw);

        assertTrue(cryptoUtils.isPwVerify(plainPw, encryptedPw));

    }
}