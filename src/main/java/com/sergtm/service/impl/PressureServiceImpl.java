package com.sergtm.service.impl;

import com.sergtm.component.WeatherDataPuller;
import com.sergtm.dao.IPressureDao;
import com.sergtm.entities.Pressure;
import com.sergtm.model.WeatherResponse;
import com.sergtm.model.weatherModel.Weather;
import com.sergtm.model.weatherModel.WeatherModel;
import com.sergtm.service.IPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PressureServiceImpl implements IPressureService {

    @Autowired
    private IPressureDao pressureDao;

    @Autowired
    private WeatherDataPuller weatherDataPuller;

    @Override
    @Transactional
    public void pull() {
        weatherDataPuller.pullFiveDaysWeatherData();
    }

    @Override
    public WeatherResponse getTodayWeather() {
        WeatherModel weatherModel = weatherDataPuller.pullTodayWeatherData();
        WeatherResponse.Builder builder = new WeatherResponse.Builder();
        return builder.setDescription(weatherModel.getWeather()[0].getDescription())
                .setIconUrl(weatherModel.getWeather()[0].getIcon())
                .setTemperature(weatherModel.getMain().getTemp()).build();
    }

    @Override
    @Transactional
    public void addAll(Map<LocalDate, Double> weatherMap) {
        for (LocalDate lc : weatherMap.keySet()) {
            Pressure pressure = pressureDao.getByDate(lc);
            if (pressure != null) {
                pressureDao.deletePressure(pressure);
            }
            Pressure pressureAdd = new Pressure();
            pressureAdd.setDate(lc);
            pressureAdd.setPressure(weatherMap.get(lc));
            pressureDao.addPressure(pressureAdd);
        }
    }
}