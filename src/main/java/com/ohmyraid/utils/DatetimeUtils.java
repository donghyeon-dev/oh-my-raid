package com.ohmyraid.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DatetimeUtils {

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String ZONE_ID = "Asia/Seoul";

    public static LocalDateTime now() {
        String now = LocalDateTime.now(ZoneId.of(ZONE_ID)).format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        return LocalDateTime.parse(now, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static LocalDateTime stringToLocalDateTime(String stringDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDateTime.parse(stringDateTime, formatter);
    }

    public static LocalDateTime unixToLocalDateTime(Long unixTimeStamp){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTimeStamp), ZoneId.of(ZONE_ID));
    }
}
