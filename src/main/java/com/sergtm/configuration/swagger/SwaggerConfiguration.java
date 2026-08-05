package com.sergtm.configuration.swagger;

import org.springdoc.core.configuration.SpringDocDataRestConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SpringDocDataRestConfiguration.class)
public class SwaggerConfiguration {
    private static final String REST_CONTROLLER_PACKAGE = "com.sergtm.health.tracker.rest.controller";

    @Bean
    public GroupedOpenApi restApi() {
        return GroupedOpenApi.builder()
                .group("health-tracker-rest")
                .packagesToScan(REST_CONTROLLER_PACKAGE)
                .pathsToExclude("/error/**")
                .build();
    }
}
