package com.ohmyraid.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DatetimeUtilsTest {

    @Autowired
    DatetimeUtils datetimeUtils;

    @Test
    void now메서드_확인() {
        System.out.println("현재날짜 ====" + datetimeUtils.now().toString());
    }

    ;

}