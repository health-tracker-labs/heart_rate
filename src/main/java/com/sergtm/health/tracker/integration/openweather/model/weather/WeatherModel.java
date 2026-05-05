package com.sergtm.health.tracker.integration.openweather.model.weather;

import lombok.Getter;

@Getter
public class WeatherModel {
    private Weather[] weather;
    private Main main;
}
