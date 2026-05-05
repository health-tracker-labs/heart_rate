package com.sergtm.service;

import com.sergtm.health.tracker.persistence.entity.Weather;

public interface IWeatherService {
    Weather getWeather();
    Weather getCurrentWeather();
}
