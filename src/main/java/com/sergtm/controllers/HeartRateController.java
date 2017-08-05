package com.sergtm.controllers;

import com.sergtm.entities.HeartRate;
import com.sergtm.entities.IEntity;
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
    IHeartRateService heartRateService;

    @RequestMapping(method = RequestMethod.GET, path = "add.json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Collection<? extends IEntity> addHeartRate(@RequestParam int upperPressure, @RequestParam int lowerPressure,
                                      @RequestParam(value = "datetime",required = false) Date datetime,
                                      @RequestParam String firstName, @RequestParam String secondName) {
        Date dt = checkParam(datetime);
        return heartRateService.createHeartRate(upperPressure,lowerPressure,dt,firstName,secondName);
    }

    @RequestMapping(method = RequestMethod.GET, path = "addById")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHeartRateById(@RequestParam Long id, @RequestParam int upperPressure, @RequestParam int lowerPressure,
                                 @RequestParam(value = "datetime",required = false) Date datetime){
        Date dt = checkParam(datetime);
        heartRateService.addHeartRateById(id, upperPressure, lowerPressure, dt);
    }

    @RequestMapping(method = RequestMethod.GET, path = "getAll.xml", produces = "application/xml")
    public Collection<HeartRate> getAll(){
        return heartRateService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "getAll.json", produces = "application/json")
    public Collection<HeartRate> getAllJSon(){
        return heartRateService.findAll();
    }

    private Date checkParam(Date date){
        if (date == null){
            return new Date();
        }else {
            return date;
        }
    }

}
