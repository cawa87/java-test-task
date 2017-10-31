package com.ronte.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ronte.api.util.SSLClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExternalAccountDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalAccountDetailsService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final SSLClient sslClient;

    @Value( "${external.service.host}" )
    private String host;

    @Value( "${external.service.port}" )
    private int port;

    @Autowired
    public ExternalAccountDetailsService(SSLClient sslClient) {
        this.sslClient = sslClient;
    }

    public String getDetails(long id) {
        try {
            RequestModel requestModel = new RequestModel(id);
            String requestMessage = objectMapper.writeValueAsString(requestModel);

            String responseMessage = sslClient.sendRequest(host, port, requestMessage);
            ResponseModel responseModel = objectMapper.readValue(responseMessage, ResponseModel.class);

            return responseModel.getAccountDetails();
        }
        catch(Exception e) {
            logger.error("Error occurred during account details loading", e);
        }

        return null;
    }

    static class RequestModel {
        private long id;

        public RequestModel(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    static class ResponseModel {
        private long id;
        private String accountDetails;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getAccountDetails() {
            return accountDetails;
        }

        public void setAccountDetails(String accountDetails) {
            this.accountDetails = accountDetails;
        }
    }
}
