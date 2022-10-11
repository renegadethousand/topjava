package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String format(LocalDateTime localDateTime, String format) {
        return DateTimeFormatter.ofPattern(format).format(localDateTime);
    }
}
