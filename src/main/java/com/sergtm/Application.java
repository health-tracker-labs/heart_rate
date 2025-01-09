package com.sergtm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import com.sergtm.configuration.db.JacksonConfiguration;
import com.sergtm.configuration.security.SecurityConfiguration;
import com.sergtm.configuration.swagger.SwaggerConfiguration;
import com.sergtm.configuration.web.WebConfig;

@SpringBootApplication
@ComponentScan("com.sergtm.configuration.db")
@Import({WebConfig.class, SecurityConfiguration.class,
		SwaggerConfiguration.class, JacksonConfiguration.class})
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Authentication authentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
