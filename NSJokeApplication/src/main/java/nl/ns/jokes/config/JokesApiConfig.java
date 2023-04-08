package nl.ns.jokes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JokesApiConfig {

    @Value("${adapters.jokes-api.base-path}")
    private String basePath;

    @Bean
    public RestTemplate jokesApiRestTemplate(){
        return new RestTemplateBuilder()
                .rootUri(basePath)
                .build();
    }

}
