package com.sergtm.form;

import java.util.Date;

public class AddHeartRateForm {
    private int lowerPressure;
    private int upperPressure;
    private int beatsPerMinute;
    private long personId;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
