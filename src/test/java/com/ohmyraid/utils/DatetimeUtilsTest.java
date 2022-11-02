package com.ohmyraid.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void StringDateTimeToLocalDateTime() {
        String stringDateTime = "2022-11-02 13:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime convertedDateTime = LocalDateTime.parse(stringDateTime, formatter);

        assertEquals(convertedDateTime, datetimeUtils.stringToLocalDateTime(stringDateTime));

    }

    ;

}