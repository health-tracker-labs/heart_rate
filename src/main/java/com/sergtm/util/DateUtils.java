package com.sergtm.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class DateUtils {
    public static LocalDateTime parseDate(String date, LocalDateTime defaultDate){
        if (date == null || StringUtils.isEmpty(date)) {
            return defaultDate;
        } else {
            return LocalDateTime.parse(date);
        }
    }
}
