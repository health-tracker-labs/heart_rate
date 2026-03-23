package com.sergtm.configuration.api;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@EnableConfigurationProperties(HealthTrackerApiClientProperties.class)
public class HealthTrackerWebClientConfig {
    @Bean
    public WebClient healthTrackerWebClient(
            HttpClient httpClient,
            HealthTrackerApiClientProperties properties
    ) {
        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public HttpClient httpClient(HealthTrackerApiClientProperties properties) {
        TimeoutProperties timeoutProperties = properties.getTimeouts();
        return HttpClient.create()
                .option(CONNECT_TIMEOUT_MILLIS, timeoutProperties.getConnectTimeout())
                .responseTimeout(timeoutProperties.getResponseTimeout())
                .doOnConnected(conn -> {
                    conn.addHandlerLast(new ReadTimeoutHandler(timeoutProperties.getReadTimeout()));
                    conn.addHandlerLast(new WriteTimeoutHandler(timeoutProperties.getWriteTimeout()));
                });
    }
}
