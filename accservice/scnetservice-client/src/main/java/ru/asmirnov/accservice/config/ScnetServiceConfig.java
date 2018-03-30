package ru.asmirnov.accservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Alexey Smirnov at 28/03/2018
 */
@Configuration
@PropertySource("classpath:scnetservice.properties")
public class ScnetServiceConfig {

    @Bean
    @ConfigurationProperties(prefix = "scnetservice")
    public ScnetServiceClientProperties scnetServiceClientProperties() {
        return new ScnetServiceClientProperties();
    }
}
