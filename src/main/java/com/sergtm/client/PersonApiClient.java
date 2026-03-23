package com.sergtm.client;

import com.sergtm.controllers.rest.response.PersonResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class PersonApiClient {
    private static final String URI = "/persons";
    @Resource
    private WebClient healthTrackerWebClient;

    public Collection<PersonResponse> getPersons() {
        return healthTrackerWebClient.get()
                .uri(URI)
                .retrieve()
                .bodyToFlux(PersonResponse.class)
                .collectList()
                .block();
    }
}
