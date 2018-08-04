package com.sergtm.service;

import com.sergtm.model.WeatherResponse;
import com.sergtm.model.weatherModel.WeatherModel;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public interface IPressureService {
    void addAll(Map<LocalDate, Double> weatherMap);
    void pull();
    WeatherResponse getTodayWeather();
}
