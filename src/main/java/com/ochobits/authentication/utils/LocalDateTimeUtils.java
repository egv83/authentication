package com.ochobits.authentication.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtils {

    public static LocalDateTime getDateTimeFormated(LocalDateTime localDateTime, String format){
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern(format);
        String dateTimeTmp = localDateTime.format(format1);

        return LocalDateTime.parse(dateTimeTmp,DateTimeFormatter.ofPattern(format));
    }
}
