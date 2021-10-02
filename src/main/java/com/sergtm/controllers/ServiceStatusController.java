package com.sergtm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sergtm.model.Response;
import com.sergtm.service.IStatusService;

@RestController
@RequestMapping("/status")
public class ServiceStatusController {
    @Autowired
    private IStatusService statusService;

    @RequestMapping(path = "/getService", method = RequestMethod.GET, produces = "application/json")
    public Response getService() {
        return statusService.identifyLastModifiedService();
    }
}