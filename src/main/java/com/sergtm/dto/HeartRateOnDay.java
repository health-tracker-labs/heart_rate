package com.sergtm.dto;

import com.sergtm.entities.HeartRate;

public class HeartRateOnDay {
    private final int lower;
    private final int upper;
    private final String date;

    public HeartRateOnDay(HeartRate heartRate) {
        this.date = heartRate.getDate().toString();
        this.lower = heartRate.getLowerPressure();
        this.upper = heartRate.getUpperPressure();
    }

    public int getLower() {
        return lower;
    }

    public int getUpper() {
        return upper;
    }

    public String getDate() {
        return date;
    }

}
