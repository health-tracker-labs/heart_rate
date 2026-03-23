package com.sergtm.configuration.api;

import lombok.Data;

import java.time.Duration;

@Data
public class TimeoutProperties {
    private int connectTimeout = 1000;
    private Duration responseTimeout = Duration.ofSeconds(3);
    private int readTimeout = 3; //in seconds
    private int writeTimeout = 3; //in seconds
}
