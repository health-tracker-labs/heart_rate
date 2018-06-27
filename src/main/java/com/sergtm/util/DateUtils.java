package com.sergtm.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DateUtils {
    //TODO: write unit test
    public static LocalDateTime parseDate(String date, LocalDateTime ldt){
        LocalDateTime defaultDate = ldt;
        if (defaultDate == null) {
            defaultDate = LocalDateTime.now();
        }

        if (StringUtils.isEmpty(date)){
            return defaultDate;
        }

        LocalDateTime parsed = null;
        try{
             parsed = LocalDateTime.parse(date);
        }catch (DateTimeParseException e){
            //nop
        }

        if (parsed == null){
            return defaultDate;
        }
        return parsed;
    }
}