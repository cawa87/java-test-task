package ru.smarty.accountapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RemoteAccountDetailService implements AccountDetailService {
    private SimpleSslPoolService poolService;
    private ObjectMapper mapper;

    public RemoteAccountDetailService(SimpleSslPoolService poolService, ObjectMapper mapper) {
        this.poolService = poolService;
        this.mapper = mapper;
    }

    @Override
    public String getAccountDetails(int accountId) {
        try {
            Future<String> response = poolService.postMessage(mapper.writeValueAsString(Collections.singletonMap("id", accountId)));
            Map map = mapper.readValue(response.get(10, TimeUnit.SECONDS), Map.class);
            return (String) map.get("accountDetails");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
