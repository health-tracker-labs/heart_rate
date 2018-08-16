package com.sergtm.dao;

import com.sergtm.entities.Weather;

public interface IWeatherDao {
    Weather getLatestWeather();
    void saveOrUpdate(Weather weather);
}
