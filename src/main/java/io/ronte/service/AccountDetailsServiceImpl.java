package io.ronte.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.ronte.exception.AccountDetailsNotFoundException;
import io.ronte.integration.SSLSocketStringClient;
import io.ronte.model.AccountDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AccountDetailsServiceImpl implements AccountDetailsService {

    private static final AccountDetailsResponse FALLBACK_RESPONSE = new AccountDetailsResponse(-1L, "unknown");
    private static final ObjectMapper mapper = new ObjectMapper();

    private final SSLSocketStringClient sslSocketStringClient;

    @Autowired
    public AccountDetailsServiceImpl(SSLSocketStringClient sslSocketStringClient) {
        this.sslSocketStringClient = sslSocketStringClient;
    }


    @HystrixCommand(fallbackMethod = "getAccountDetailsFallback")
    @Override
    public AccountDetailsResponse getAccountDetails(long id) {

        String response = sslSocketStringClient.sendMessage("{\"id\":" + id + "}");
        try {
            return mapper.readValue(response, AccountDetailsResponse.class);
        } catch (IOException e) {
            throw new AccountDetailsNotFoundException(id, e);
        }
    }

    public AccountDetailsResponse getAccountDetailsFallback(long id) {
        return FALLBACK_RESPONSE;
    }
}
