package com.test.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.properties.AccountDetailsProperties;
import com.test.services.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

@Service
public class TlsAccountDetailsService implements AccountDetailsService {

    private final AccountDetailsProperties accountDetailsProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public TlsAccountDetailsService(AccountDetailsProperties accountDetailsProperties,
                                    ObjectMapper objectMapper) {
        this.accountDetailsProperties = accountDetailsProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getAccountDetails(Long id) {
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket sslsocket = (SSLSocket) sslsocketfactory
                .createSocket(accountDetailsProperties.getServiceUri(), accountDetailsProperties.getServicePort())) {
            AccountDetailsRequest accountDetailsRequest = new AccountDetailsRequest();
            accountDetailsRequest.setId(id);
            return getDetails(sslsocket, accountDetailsRequest).getAccountDetails();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AccountDetailsResponse getDetails(Socket socket, AccountDetailsRequest accountDetailsRequest) throws IOException {
        InputStream inputstream = socket.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

        OutputStream outputstream = socket.getOutputStream();
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
        BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

        bufferedwriter.write(objectMapper.writeValueAsString(accountDetailsRequest) + '\n');
        bufferedwriter.flush();

        return objectMapper.readValue(bufferedreader, AccountDetailsResponse.class);
    }


    private static class AccountDetailsRequest {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    private static class AccountDetailsResponse {
        private Long id;
        private String accountDetails;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
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
