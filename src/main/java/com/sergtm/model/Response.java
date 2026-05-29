package com.sergtm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private ServiceName serviceName;
    private Duration timeBeforeAvailability;
}
