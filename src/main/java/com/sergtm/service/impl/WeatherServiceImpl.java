package com.sergtm.service.impl;

import com.sergtm.component.WeatherDataPuller;
import com.sergtm.dao.IWeatherDao;
import com.sergtm.entities.Weather;
import com.sergtm.model.ServiceName;
import com.sergtm.model.weatherModel.WeatherModel;
import com.sergtm.service.IStatusService;
import com.sergtm.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WeatherServiceImpl implements IWeatherService {
    private static final String OPEN_WEATHER_MAP_URL = "http://openweathermap.org/img/w/%s.png";

    @Autowired
    private WeatherDataPuller weatherDataPuller;

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
        WeatherModel weatherModel = weatherDataPuller.pullTodayWeatherData();

        statusService.updateAndSave(ServiceName.WeatherService);

        return getAndUpdateWeather(weatherModel);
    }

    private Weather getAndUpdateWeather(WeatherModel weatherModel){
        Weather weather = weatherDao.getLatestWeather().orElse(new Weather());

        weather.setIconUrl(String.format(OPEN_WEATHER_MAP_URL, weatherModel.getWeather()[0].getIcon()));
        weather.setTemperature(weatherModel.getMain().getTemp());
        weather.setDescription(weatherModel.getWeather()[0].getDescription());
        weather.setDate(LocalDateTime.now());

        weatherDao.saveOrUpdate(weather);

        return weather;
    }
}
