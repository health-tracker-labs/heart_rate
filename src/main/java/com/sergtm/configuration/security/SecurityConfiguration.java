package com.sergtm.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/WEB-INF/**", "/error", "/webjars/**", "/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers("/", "/static/index.html").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/person/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/heartRate/**").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(login ->
                        login.loginPage("/login")
                                .permitAll())
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                                .deleteCookies("JSESSIONID"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
