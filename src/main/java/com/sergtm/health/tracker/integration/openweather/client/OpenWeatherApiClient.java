package com.sergtm.health.tracker.integration.openweather.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Collections.singletonList;

@Component
public class OpenWeatherApiClient {
    private static final String RESPONSE_BODY_IS_EMPTY = "Response body is empty";

    private final RestTemplate restTemplate;

    public OpenWeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
            throw new IllegalStateException(RESPONSE_BODY_IS_EMPTY);
        }

        return response.getBody();
    }
}
