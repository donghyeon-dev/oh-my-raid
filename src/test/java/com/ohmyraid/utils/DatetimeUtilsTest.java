package com.ohmyraid.utils;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class DatetimeUtilsTest {

    @Test
    void now메서드_확인() {
        System.out.println("현재날짜 ====" + DatetimeUtils.now().toString());
    }

    @Test
    void StringDateTimeToLocalDateTime() {
        String stringDateTime = "2022-11-02 13:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime convertedDateTime = LocalDateTime.parse(stringDateTime, formatter);

        assertEquals(convertedDateTime, DatetimeUtils.stringToLocalDateTime(stringDateTime));

    };

    @Test
    void unixTimestampToLocalDateTime(){
        String unixTimestamp = "1519466570000";
        LocalDateTime convertedTime = DatetimeUtils.unixToLocalDateTime(Long.parseLong(unixTimestamp));
        log.info("convertedTime is {}", convertedTime);
    }

}