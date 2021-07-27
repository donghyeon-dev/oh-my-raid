package com.ohmyraid.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilsTest {

    @Test
    void 통과해야하는_getAuthValue() {
        String result = CryptoUtils.getAuthValue();
        assertEquals(result, "Basic Y2Q1ZjJjYzIwZjBlNGJlMDhlMzFhZTk5MzhlNTZiMmQ6MXBlblN0VG9wb2tnUmhNNGZSdEd3ZXoySlV3eWM3SzI=");
    }
}