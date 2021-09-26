package com.sergtm.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import com.sergtm.configuration.swagger.SwaggerConfiguration;
import com.sergtm.configuration.web.WebConfig;

@Configuration
@ComponentScan("com.sergtm.configuration.db")
@Import({WebConfig.class, SwaggerConfiguration.class})
@EnableScheduling
@SpringBootApplication
public class SpringBootConfiguration extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootConfiguration.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConfiguration.class, args);
    }

    @Bean
    public RestTemplate RestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Authentication Authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
