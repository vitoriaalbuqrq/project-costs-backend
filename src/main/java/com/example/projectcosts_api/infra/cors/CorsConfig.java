package com.example.projectcosts_api.infra.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowedOrigin = System.getenv("ALLOWED_ORIGIN");
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigin != null ? allowedOrigin : "http://localhost:4200")
                .allowedMethods("GET", "POST", "DELETE", "PUT");
    }
}
