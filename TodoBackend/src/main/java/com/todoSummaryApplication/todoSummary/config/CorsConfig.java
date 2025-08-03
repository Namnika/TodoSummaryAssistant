package com.todoSummaryApplication.todoSummary.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // allowed all paths
                        .allowedOrigins(frontendUrl) // allowed React App origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // allowed all methods
                        .allowedHeaders("*"); // allowed all headers

            }
        };
    }
}
