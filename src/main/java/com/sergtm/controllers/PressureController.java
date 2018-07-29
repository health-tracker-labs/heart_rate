package com.sergtm.controllers;

import com.sergtm.service.IPressureService;
import com.sergtm.service.impl.PressureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/pressure")
public class PressureController {

    private static final Logger LOG = Logger.getLogger(PressureController.class.getName());

    @Autowired
    private IPressureService pressureService;

    @RequestMapping(path = "/pull.do", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void pull() {
        try {
            pressureService.pull();
        } catch (RestClientException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}