package com.sergtm.health.tracker.integration.openweather;

public interface WeatherDataPuller {
    void updateWeatherData();
    WeatherPullerType getType();
}
