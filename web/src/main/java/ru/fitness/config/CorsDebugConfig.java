package ru.fitness.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsDebugConfig {
    @Value("${fitness.web.allow-cors:false}")
    private boolean allowCors;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if (allowCors) {
                    registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
                }
            }
        };
    }
}
