package com.sergtm.controllers;

import com.sergtm.component.WeatherDataPuller;
import com.sergtm.dao.IPressureDao;
import com.sergtm.entities.Pressure;
import com.sergtm.service.IPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/pressure")
public class PressureController {

    @Autowired
    private WeatherDataPuller weatherDataPuller;

    @RequestMapping(path = "/pull.do", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void putPressure() {
        weatherDataPuller.pull();
    }
}