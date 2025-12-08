package com.sysnormal.libs.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Datetime utils
 *
 * @author Alencar
 * @version 1.0.0
 */
public class DatetimeUtils {

    public static String getLocalDateTimeUtc(LocalDateTime baseDateTime,
                                             Integer hour,
                                             Integer minute,
                                             Integer second) {
        // garante que baseDateTime nunca seja nulo
        LocalDateTime dateTime = (baseDateTime != null) ? baseDateTime : LocalDateTime.now(ZoneOffset.UTC);

        int h = (hour != null) ? hour : 0;
        int m = (minute != null) ? minute : 0;
        int s = (second != null) ? second : 0;

        return dateTime
                .withHour(h)
                .withMinute(m)
                .withSecond(s)
                .withNano(0) // zera nanos
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static String getLocalDateTimeUtc(Integer hour, Integer minute, Integer second) {
        return getLocalDateTimeUtc(LocalDateTime.now(), hour, minute, second);
    }

    public static String getLocalDateTimeUtc(LocalDateTime baseDateTime) {
        return getLocalDateTimeUtc(baseDateTime,baseDateTime.getHour(),baseDateTime.getMinute(),baseDateTime.getSecond());
    }

    public static String getLocalDateTimeUtc() {
        return getLocalDateTimeUtc(LocalDateTime.now());
    }

}
