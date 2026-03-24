package com.sergtm.configuration.api;

import io.netty.channel.ChannelOption;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.util.Map;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthTrackerWebClientConfigTest {
    private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 1000;
    private static final int NON_DEFAULT_CONNECT_TIMEOUT_MILLIS = 5000;
    private static final Duration DEFAULT_RESPONSE_TIMEOUT = Duration.ofSeconds(3);
    private static final Duration NON_DEFAULT_RESPONSE_TIMEOUT = Duration.ofSeconds(5);
    private static final @NotBlank String DEFAULT_BASE_URL = "http://localhost:8080";

    private final HealthTrackerWebClientConfig testedInstance = new HealthTrackerWebClientConfig();

    @Test
    void shouldSetupDefaultConnectionTimeoutValueWhenTimeoutPropertiesAreNotPresent() {
        HealthTrackerApiClientProperties properties = new HealthTrackerApiClientProperties();
        HttpClient httpClient = testedInstance.httpClient(properties);

        Integer connectionTimeoutMillis = (Integer)getOption(httpClient, CONNECT_TIMEOUT_MILLIS);
        assertEquals(DEFAULT_CONNECT_TIMEOUT_MILLIS, connectionTimeoutMillis);
    }

    @Test
    void shouldSetupDefaultResponseTimeoutValueWhenTimeoutPropertiesAreNotPresent() {
        HealthTrackerApiClientProperties properties = new HealthTrackerApiClientProperties();
        HttpClient httpClient = testedInstance.httpClient(properties);

        Duration responseTimeout = httpClient.configuration().responseTimeout();
        assertEquals(DEFAULT_RESPONSE_TIMEOUT, responseTimeout);
    }

    @Test
    void shouldSetupNonDefaultConnectionTimeoutValueWhenTimeoutPropertiesArePresent() {
        TimeoutProperties timeoutProperties = new TimeoutProperties();
        timeoutProperties.setConnectTimeout(NON_DEFAULT_CONNECT_TIMEOUT_MILLIS);

        HealthTrackerApiClientProperties properties = new HealthTrackerApiClientProperties();
        properties.setTimeouts(timeoutProperties);

        HttpClient httpClient = testedInstance.httpClient(properties);

        Integer connectionTimeoutMillis = (Integer)getOption(httpClient, CONNECT_TIMEOUT_MILLIS);
        assertEquals(NON_DEFAULT_CONNECT_TIMEOUT_MILLIS, connectionTimeoutMillis);
    }

    @Test
    void shouldSetupNonDefaultResponseTimeoutValueWhenTimeoutPropertiesArePresent() {
        TimeoutProperties timeoutProperties = new TimeoutProperties();
        timeoutProperties.setResponseTimeout(NON_DEFAULT_RESPONSE_TIMEOUT);

        HealthTrackerApiClientProperties properties = new HealthTrackerApiClientProperties();
        properties.setTimeouts(timeoutProperties);

        HttpClient httpClient = testedInstance.httpClient(properties);

        Duration responseTimeout = httpClient.configuration().responseTimeout();
        assertEquals(NON_DEFAULT_RESPONSE_TIMEOUT, responseTimeout);
    }

    private static Object getOption(HttpClient httpClient, ChannelOption<?> option) {
        Map<ChannelOption<?>, ?> options = httpClient.configuration().options();
        return options.get(option);
    }
}