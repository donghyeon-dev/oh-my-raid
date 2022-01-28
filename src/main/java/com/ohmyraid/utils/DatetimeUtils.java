package com.ohmyraid.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class DatetimeUtils {

    public LocalDateTime now() {

        String now = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.debug("nowTime is {}", LocalDateTime.parse(now, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return LocalDateTime.parse(now, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
