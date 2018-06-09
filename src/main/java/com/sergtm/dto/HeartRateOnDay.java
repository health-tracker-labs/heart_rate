package com.sergtm.dto;

import com.sergtm.entities.HeartRate;

public class HeartRateOnDay {
    private final int lower;
    private final int upper;
    private final int beatsPerMinute;
    private final String date;

    public HeartRateOnDay(HeartRate heartRate) {
        this.date = heartRate.getDate().toString();
        this.lower = heartRate.getLowerPressure();
        this.upper = heartRate.getUpperPressure();
        this.beatsPerMinute = heartRate.getBeatsPerMinute();
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

    public int getBeatsPerMinute(){return beatsPerMinute;}
}
