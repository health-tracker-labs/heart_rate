package com.sergtm.service.impl;

import com.sergtm.component.WeatherDataPuller;
import com.sergtm.dao.IPressureDao;
import com.sergtm.entities.Pressure;
import com.sergtm.service.IPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

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