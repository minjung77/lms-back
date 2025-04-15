package project.lmsback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 꼭 명시!
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true); // CORS + 토큰 인증에서 핵심
    }

    @Bean
    public RestTemplate restTemplate() {
     RestTemplate restTemplate = new RestTemplate();

     // UTF-8 인코딩 설정
    restTemplate.getMessageConverters().add(
    0,new StringHttpMessageConverter(StandardCharsets.UTF_8)
    );

     return restTemplate;
    }
}
