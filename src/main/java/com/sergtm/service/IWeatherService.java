package com.sergtm.service;

import com.sergtm.entities.Weather;

public interface IWeatherService {
    Weather getWeather();
    Weather getCurrentWeather();
}
