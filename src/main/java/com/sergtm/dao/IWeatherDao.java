package com.sergtm.dao;

import com.sergtm.entities.Weather;

import java.util.Optional;

public interface IWeatherDao {
    Optional<Weather> getLatestWeather();
    void saveOrUpdate(Weather weather);
}
