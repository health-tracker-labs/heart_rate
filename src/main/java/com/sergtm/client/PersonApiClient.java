package com.sergtm.client;

import com.sergtm.controllers.rest.response.PersonResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

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
                .exchangeToFlux(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToFlux(PersonResponse.class);
                    }
                    return response.bodyToMono(String.class)
                            .flatMapMany(body -> Flux.error(new RuntimeException(body)));
                })
                .collectList()
                .block();
    }
}
