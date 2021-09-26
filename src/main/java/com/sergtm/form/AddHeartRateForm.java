package com.sergtm.form;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class AddHeartRateForm {
    private int lowerPressure;
    private int upperPressure;
    private int beatsPerMinute;
    private long personId;

    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getLowerPressure() {
        return lowerPressure;
    }
    public void setLowerPressure(int lowerPressure) {
        this.lowerPressure = lowerPressure;
    }

    public int getUpperPressure() {
        return upperPressure;
    }
    public void setUpperPressure(int upperPressure) {
        this.upperPressure = upperPressure;
    }

    public int getBeatsPerMinute() {
        return beatsPerMinute;
    }
    public void setBeatsPerMinute(int beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
    }

    public long getPersonId() {
        return personId;
    }
    public void setPersonId(long personId) {
        this.personId = personId;
    }
}
