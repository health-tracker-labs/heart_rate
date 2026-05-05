package com.sergtm.health.tracker.integration.openweather.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static java.util.Collections.singletonList;

@Component
public class OpenWeatherApiClient {
    @Autowired
    private RestTemplate restTemplate;

    public <T> T exchange(String url, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                clazz);

        if (!response.hasBody()) {
            Objects.requireNonNull(response.getBody());
        }

        return response.getBody();
    }
}
