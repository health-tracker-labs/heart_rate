package com.sergtm.controllers.api;

import com.sergtm.client.PersonApiClient;
import com.sergtm.controllers.rest.response.PersonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;

@RestController("personApiController")
@RequestMapping(path = "/api/persons", produces = "application/json")
public class PersonController {
    @Resource
    private PersonApiClient personApiClient;

    @GetMapping
    public Collection<PersonResponse> getPersons() {
        return personApiClient.getPersons();
    }
}
