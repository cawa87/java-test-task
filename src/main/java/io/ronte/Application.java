package io.ronte;

import io.ronte.integration.SSLSocketStringClient;
import io.ronte.integration.SSLSocketStringClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.net.ssl.SSLException;

@EnableCircuitBreaker
@SpringBootApplication
@EnableSwagger2
public class Application {

    @Value("${account.details.service.host}")
    private String host;
    @Value("${account.details.service.port}")
    private int port;
    @Value("${account.details.service.pool.maxConnectionsCount}")
    private int poolMaxConnectionsCount;
    @Value("${account.details.service.pool.maxPendingAcquires}")
    private int poolMaxPendingAcquires;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public SSLSocketStringClient sslSocketStringClient() throws SSLException {
        return new SSLSocketStringClientImpl(host, port, poolMaxConnectionsCount, poolMaxPendingAcquires);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
