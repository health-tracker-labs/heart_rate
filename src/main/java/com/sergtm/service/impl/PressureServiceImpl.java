package com.sergtm.service.impl;

import com.sergtm.dao.IPressureDao;
import com.sergtm.entities.Pressure;
import com.sergtm.service.IPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PressureServiceImpl implements IPressureService {
    @Autowired
    private IPressureDao pressureDao;

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