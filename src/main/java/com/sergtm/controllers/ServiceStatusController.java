package com.sergtm.controllers;

import com.sergtm.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class ServiceStatusController {
    @Autowired
    private IStatusService statusService;

    @RequestMapping(path = "/getService", method = RequestMethod.GET)
    public String getService() {
        return statusService.serviceToCall();
    }
}
