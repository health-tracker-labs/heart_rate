package com.sergtm.controllers;

import com.sergtm.component.WeatherDataPuller;
import com.sergtm.dao.IPressureDao;
import com.sergtm.entities.Pressure;
import com.sergtm.service.IPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/pressure")
public class PressureController {

    @Autowired
    private WeatherDataPuller weatherDataPuller;

    @Autowired
    private IPressureDao pressureDao;

    @Autowired
    private IPressureService pressureService;

    @RequestMapping("/put")
    @ResponseStatus(HttpStatus.OK)
    public void putPressure(){
        weatherDataPuller.pull();
    }

    @RequestMapping(value = "/get", produces = "application/json")
    public Collection<Pressure> getPressure(){
        return pressureDao.getAll();
    }
}