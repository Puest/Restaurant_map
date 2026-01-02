package com.restaurant.food.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF(Cross Site Request Forgery) 보호 기능 비활성화
                .authorizeHttpRequests(auth -> auth // HTTP 요청에 대한 접근 권한 설정
                        .requestMatchers("/**").permitAll()
                );
        return http.build();
    }
}
