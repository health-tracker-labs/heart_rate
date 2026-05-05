package com.sergtm.dao;

import com.sergtm.health.tracker.persistence.entity.Weather;

import java.util.Optional;

public interface IWeatherDao {
    Optional<Weather> getLatestWeather();
    void saveOrUpdate(Weather weather);
}
