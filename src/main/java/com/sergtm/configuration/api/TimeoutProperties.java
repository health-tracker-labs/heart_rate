package com.sergtm.configuration.api;

import lombok.Data;

import java.time.Duration;

@Data
public class TimeoutProperties {
    private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 1000;
    private static final Duration DEFAULT_RESPONSE_TIMEOUT = Duration.ofSeconds(3);

    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT_MILLIS;
    private Duration responseTimeout = DEFAULT_RESPONSE_TIMEOUT;
    private int readTimeout = 3; //in seconds
    private int writeTimeout = 3; //in seconds
}
