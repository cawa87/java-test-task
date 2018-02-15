package com.accounts.dao;

import com.accounts.api.model.Account;
import com.accounts.api.model.AccountDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;

/**
 * Created by pasha on 14.02.18.
 */
@Repository
public class AccountDetailRepository {

    @Value("${tls.url}")
    private String url;
    @Value("${tls.port}")
    private int port;

    private ObjectMapper objectMapper = new ObjectMapper();
    private SSLSocket sslsocket;
    private BufferedWriter bufferedwriter;
    private BufferedReader bufferedreader;

    @PostConstruct
    private void init() {
        try {
            InetAddress adr = InetAddress.getByName(url);

            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            sslsocket = (SSLSocket) sslsocketfactory.createSocket(adr, port);

            OutputStream outputstream = sslsocket.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            bufferedwriter = new BufferedWriter(outputstreamwriter);

            InputStream inputstream = sslsocket.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            bufferedreader = new BufferedReader(inputstreamreader);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AccountDetail readAccountDetail(Account account) {
        try {

            String sendPacket = objectMapper.writeValueAsString(account) + "\n";
            bufferedwriter.write(sendPacket);
            bufferedwriter.flush();

            String string = bufferedreader.readLine();

            AccountDetail result = objectMapper.readValue(string, AccountDetail.class);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            init(); // try to reinit
        }
        return null;
    }

}
