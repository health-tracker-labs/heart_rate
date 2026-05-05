package com.sergtm.health.tracker.integration.openweather.strategy;

import com.sergtm.dao.IWeatherDao;
import com.sergtm.health.tracker.integration.openweather.WeatherDataPuller;
import com.sergtm.health.tracker.integration.openweather.WeatherPullerType;
import com.sergtm.health.tracker.integration.openweather.client.OpenWeatherApiClient;
import com.sergtm.health.tracker.integration.openweather.mapper.WeatherMapper;
import com.sergtm.health.tracker.integration.openweather.model.weather.WeatherModel;
import com.sergtm.health.tracker.persistence.entity.Weather;
import com.sergtm.model.ServiceName;
import com.sergtm.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.TODAY;

@Component
public class TodayWeatherDataPuller implements WeatherDataPuller {
    @Value("${currentWeatherUrl}")
    private String currentWeatherUrl;

    @Autowired
    private IStatusService statusService;
    @Autowired
    private IWeatherDao weatherDao;
    @Autowired
    private WeatherMapper weatherMapper;

    private final OpenWeatherApiClient helper;

    public TodayWeatherDataPuller(OpenWeatherApiClient openWeatherApiHelper) {
        this.helper = openWeatherApiHelper;
    }

    @Override
    @Transactional
    public void updateWeatherData() {
        try {
            WeatherModel weatherModel = helper.exchange(currentWeatherUrl, WeatherModel.class);

            Weather weather = weatherDao.getLatestWeather().orElse(new Weather());
            weatherMapper.update(weatherModel, weather);

            weatherDao.saveOrUpdate(weather);
        } finally {
            statusService.updateAndSave(ServiceName.WeatherService);
        }
    }

    @Override
    public WeatherPullerType getType() {
        return TODAY;
    }
}
