package org.example.calculatorbackend.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CalculatorConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/v1/**")
                .allowedOrigins("http://localhost:3000")  // Allowing CORS from localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Add other methods as necessary
    }
}