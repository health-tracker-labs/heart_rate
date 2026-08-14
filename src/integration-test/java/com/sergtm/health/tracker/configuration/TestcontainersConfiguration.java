package com.sergtm.health.tracker.configuration;

import lombok.SneakyThrows;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.oracle.OracleContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {
    private static final String DOCKER_IMAGE_NAME = "gvenzl/oracle-free:23.26.0-slim-faststart";

    @Bean
    @ServiceConnection
    @SneakyThrows
    public OracleContainer oracleContainer() {
        return new OracleContainer(DOCKER_IMAGE_NAME);
    }
}
