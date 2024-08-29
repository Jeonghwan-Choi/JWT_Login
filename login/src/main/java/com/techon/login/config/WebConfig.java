package com.techon.login.config;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")  // Apply to all endpoints
        .allowedOrigins(
            "http://localhost:3001",
            "http://localhost:3001/",
            "http://127.0.0.1:3001",
            "http://127.0.0.1:3001/",
            "http://127.0.0.1:5000",
            "http://127.0.0.1:5000/",
            "https://mestechon.com/",
            "https://mestechon.com")  // Allow specific origins
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow specific HTTP methods
        .allowedHeaders("*")  // Allow all headers
        .allowCredentials(true);  // Allow credentials (e.g., cookies)
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // Ensure UTF-8 encoding for both String and JSON message converters
    converters.stream()
        .filter(converter -> converter instanceof StringHttpMessageConverter)
        .map(converter -> (StringHttpMessageConverter) converter)
        .forEach(converter -> converter.setDefaultCharset(StandardCharsets.UTF_8));

    converters.stream()
        .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
        .map(converter -> (MappingJackson2HttpMessageConverter) converter)
        .forEach(converter -> converter.setDefaultCharset(StandardCharsets.UTF_8));
  }
}
