package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;


/**
 * Created by radu.
 */

@Configuration
@EnableWebMvc
@ComponentScan({"web.controller", "web.converter"})
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .exposedHeaders("Access-Control-Allow-Origin:*")
                        .allowedOrigins("http://localhost:4200", "http://localhost:8080","http://localhost:8082")
                        .allowedMethods("GET", "PUT", "POST", "DELETE","OPTIONS")
                        //.exposedHeaders("Access-Control-Allow-Credentials")
                        .allowCredentials(true)
                        .allowedHeaders("Content-Type");
            }
        };
    }
}
