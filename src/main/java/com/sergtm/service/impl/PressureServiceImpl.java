package com.sergtm.service.impl;

import com.sergtm.component.WeatherDataPuller;
import com.sergtm.dao.IPressureDao;
import com.sergtm.dao.IServiceStatusDao;
import com.sergtm.entities.Pressure;
import com.sergtm.entities.ServiceStatus;
import com.sergtm.model.ServiceName;
import com.sergtm.model.WeatherResponse;
import com.sergtm.model.weatherModel.WeatherModel;
import com.sergtm.service.IPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PressureServiceImpl implements IPressureService {

    @Autowired
    private IPressureDao pressureDao;

    @Autowired
    private WeatherDataPuller weatherDataPuller;

    @Autowired
    private IServiceStatusDao serviceStatusDao;

    @Override
    @Transactional
    public void pull() {
        weatherDataPuller.pullFiveDaysWeatherData();
    }

    @Override
    @Transactional
    public WeatherResponse getTodayWeather() {
        ServiceStatus weatherService = serviceStatusDao.getByName(ServiceName.WeatherService);
        WeatherModel weatherModel = weatherDataPuller.pullTodayWeatherData();
        WeatherResponse.Builder builder = new WeatherResponse.Builder();
        weatherService.setLastModificationTime(LocalDateTime.now());
        serviceStatusDao.update(weatherService);
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