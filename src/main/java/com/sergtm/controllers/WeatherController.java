package com.sergtm.controllers;

import com.sergtm.entities.Weather;
import com.sergtm.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private IWeatherService weatherService;

    @RequestMapping(path = "/getTodayWeatherUrl.json", method = RequestMethod.GET, produces = "application/json")
    public Weather getWeather() {
        return weatherService.getCurrentWeather();
    }

    @RequestMapping(path = "/getOnRender.json", method = RequestMethod.GET, produces = "application/json")
    public Weather getOnRender() {
        return weatherService.getWeather();
    }
}
