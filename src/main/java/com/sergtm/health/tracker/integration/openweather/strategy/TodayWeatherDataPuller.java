package com.sergtm.health.tracker.integration.openweather.strategy;

import com.sergtm.dao.IWeatherDao;
import com.sergtm.health.tracker.integration.openweather.WeatherDataPuller;
import com.sergtm.health.tracker.integration.openweather.WeatherPullerType;
import com.sergtm.health.tracker.integration.openweather.client.OpenWeatherApiClient;
import com.sergtm.health.tracker.integration.openweather.mapper.WeatherMapper;
import com.sergtm.health.tracker.integration.openweather.model.weather.WeatherModel;
import com.sergtm.health.tracker.persistence.entity.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.TODAY;

@Component
public class TodayWeatherDataPuller implements WeatherDataPuller {
    @Value("${open-weather.today-weather-url}")
    private String todayWeatherUrl;

    private final OpenWeatherApiClient helper;
    private final IWeatherDao weatherDao;
    private final WeatherMapper weatherMapper;

    public TodayWeatherDataPuller(
            OpenWeatherApiClient openWeatherApiHelper,
            IWeatherDao weatherDao,
            WeatherMapper weatherMapper
    ) {
        this.helper = openWeatherApiHelper;
        this.weatherDao = weatherDao;
        this.weatherMapper = weatherMapper;
    }

    @Override
    @Transactional
    public void updateWeatherData() {
        WeatherModel weatherModel = helper.exchange(todayWeatherUrl, WeatherModel.class);

        Weather weather = weatherDao.getLatestWeather().orElse(new Weather());
        weatherMapper.update(weatherModel, weather);

        weatherDao.saveOrUpdate(weather);
    }

    @Override
    public WeatherPullerType getType() {
        return TODAY;
    }
}
