package com.sergtm.controllers;

import com.sergtm.dto.StatisticOnDay;
import com.sergtm.entities.IEntity;
import com.sergtm.form.AddHeartRateForm;
import com.sergtm.service.IHeartRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/heartRate")
public class HeartRateController {
    @Autowired
    private IHeartRateService heartRateService;

    @RequestMapping(method = RequestMethod.GET, path = "add.json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Collection<? extends IEntity> addHeartRate(@RequestParam int upperPressure, @RequestParam int lowerPressure,
                                                      @RequestParam int beatsPerMinute,
                                                      @RequestParam(value = "datetime", required = false) Date datetime,
                                                      @RequestParam String firstName, @RequestParam String secondName) {
        Date dt = checkParam(datetime);
        return heartRateService.createHeartRate(upperPressure, lowerPressure, beatsPerMinute, dt, firstName, secondName);
    }

    @RequestMapping(method = RequestMethod.GET, path = "addById")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHeartRateById(@RequestParam Long id, @RequestParam int upperPressure, @RequestParam int lowerPressure,
                                 @RequestParam int beatsPerMinute,
                                 @RequestParam(value = "datetime", required = false) Date datetime) {
        Date dt = checkParam(datetime);
        heartRateService.addHeartRateById(id, upperPressure, lowerPressure, beatsPerMinute, dt);
    }

    @RequestMapping(method = RequestMethod.GET, path = "getByPage.xml", produces = "application/xml")
    public Collection<? extends IEntity> getAll(@RequestParam(defaultValue = "1") int page) {
        return heartRateService.findByPage(page);
    }

    @RequestMapping(method = RequestMethod.GET, path = "getByPage.json", produces = "application/json")
    public Collection<? extends IEntity> getAllJSon(@RequestParam(defaultValue = "1") int page) {
        return heartRateService.findByPage(page);
    }

    @RequestMapping(path = "delete.do")
    public boolean delete(@RequestParam Long id) {
        return heartRateService.deleteHeartRate(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "chart.json", produces = "application/json")
    public Collection<StatisticOnDay> getChartData(Long personId) {
        return heartRateService.getChartData(personId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "save.do")
    public void save(@ModelAttribute AddHeartRateForm form) {
        heartRateService.createHeartRate(form);
    }

    private Date checkParam(Date date) {
        if (date == null) {
            return new Date();
        } else {
            return date;
        }
    }
}