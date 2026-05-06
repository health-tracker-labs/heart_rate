package com.sergtm.service.impl;

import com.sergtm.dao.IWeatherDao;
import com.sergtm.health.tracker.integration.openweather.WeatherPullerProvider;
import com.sergtm.health.tracker.persistence.entity.Weather;
import com.sergtm.model.ServiceName;
import com.sergtm.service.IStatusService;
import com.sergtm.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.FIVE_DAYS_FORECAST;
import static com.sergtm.health.tracker.integration.openweather.WeatherPullerType.TODAY;
import static com.sergtm.model.ServiceName.PressureService;

@Service
public class WeatherServiceImpl implements IWeatherService {
    private static final String OPEN_WEATHER_MAP_URL = "http://openweathermap.org/img/w/%s.png";

    @Autowired
    private WeatherPullerProvider weatherPullerProvider;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private IWeatherDao weatherDao;

    @Transactional
    @Override
    public Weather getWeather() {
        if (statusService.identifyLastModifiedService().getServiceName().equals(ServiceName.None)) {
            return weatherDao.getLatestWeather().orElseGet(this::getCurrentWeather);
        } else {
            return getCurrentWeather();
        }
    }

    @Override
    @Transactional
    public Weather getCurrentWeather() {
        weatherPullerProvider.get(TODAY).updateWeatherData();
        statusService.updateAndSave(ServiceName.WeatherService);

        return weatherDao.getLatestWeather().orElse(new Weather());
    }

    @Transactional
    public void update5DaysForecast() {
        weatherPullerProvider.get(FIVE_DAYS_FORECAST).updateWeatherData();
        statusService.updateAndSave(PressureService);
    }
}
