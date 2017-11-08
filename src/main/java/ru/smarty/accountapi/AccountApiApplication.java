package ru.smarty.accountapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.smarty.accountapi.services.AccountDetailService;
import ru.smarty.accountapi.services.DummyAccountDetailService;
import ru.smarty.accountapi.services.RemoteAccountDetailService;
import ru.smarty.accountapi.services.SimpleSslPoolService;

@SpringBootApplication
public class AccountApiApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AccountApiApplication.class, args);
    }

    @Bean
    @Profile("test")
    public AccountDetailService testService() {
        return new DummyAccountDetailService();
    }

    @Bean
    @ConditionalOnMissingBean(AccountDetailService.class)
    public AccountDetailService prodService(SimpleSslPoolService pool, ObjectMapper mapper) {
        return new RemoteAccountDetailService(pool, mapper);
    }
}
