package org.example.branch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // This is somewhat overkill, but done in order to mock the RestTemplate for the unit tests
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

