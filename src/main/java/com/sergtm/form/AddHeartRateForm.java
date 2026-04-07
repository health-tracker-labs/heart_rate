package com.sergtm.form;

import com.sergtm.TimeOfDay;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class AddHeartRateForm {
    private int lowerPressure;
    private int upperPressure;
    private int beatsPerMinute;
    private long personId;
    private TimeOfDay timeOfDay;

    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime date;
}
