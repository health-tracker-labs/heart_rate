package com.sergtm.dto;

import com.sergtm.entities.HeartRateWithWeatherPressure;

public class StatisticOnDay {
    private Long lower;
    private Long upper;
    private Long beatsPerMinute;
    private Double weatherPressure;
    private String date;

    public StatisticOnDay(HeartRateWithWeatherPressure p) {
        this.date = p.getDate().toString();
        this.lower = p.getLowerPressure();
        this.upper = p.getUpperPressure();
        this.weatherPressure = p.getWeatherPressure();
        this.beatsPerMinute = p.getBeatsPerMinute();
    }

    public Long getLower() {
        return lower;
    }

    public void setLower(Long lower) {
        this.lower = lower;
    }

    public Long getUpper() {
        return upper;
    }

    public void setUpper(Long upper) {
        this.upper = upper;
    }

    public Long getBeatsPerMinute() {
        return beatsPerMinute;
    }

    public void setBeatsPerMinute(Long beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
    }

    public Double getWeatherPressure() {
        return weatherPressure;
    }

    public void setWeatherPressure(Double weatherPressure) {
        this.weatherPressure = weatherPressure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}