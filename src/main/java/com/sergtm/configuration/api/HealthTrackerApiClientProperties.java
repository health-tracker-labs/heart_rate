package com.sergtm.configuration.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties(prefix = "health-tracker.api")
public class HealthTrackerApiClientProperties {
    @NotBlank
    private String baseUrl;

    private TimeoutProperties timeouts;
}
