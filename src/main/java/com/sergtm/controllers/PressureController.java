package com.sergtm.controllers;

import com.sergtm.model.WeatherResponse;
import com.sergtm.service.IPressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pressure")
public class PressureController {

    @Autowired
    private IPressureService pressureService;

    @RequestMapping(path = "/pull.do", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void pull() {
        pressureService.pull();
    }


    @RequestMapping(path = "/getTodayWeatherUrl.json", method = RequestMethod.GET, produces = "application/json")
    public WeatherResponse getWeather() {
        return pressureService.getTodayWeather();
    }
}