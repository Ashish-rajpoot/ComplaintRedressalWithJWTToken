package com.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**") // Allow all origins, change this to your frontend URL if needed
                .allowedOrigins("http://localhost:4200","https://complainredressal-beg3b5evbxhybbdp.canadacentral-01.azurewebsites.net")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Add any additional HTTP methods you need
                .allowedHeaders("*")
                .allowCredentials(true);
//                .maxAge(3600);
    }
}
