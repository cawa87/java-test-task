package com.accounts;

import com.accounts.api.model.AccountDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.*;

import static org.junit.Assert.assertNotNull;

/**
 * Created by pasha on 14.02.18.
 */
public class SSLConnectTest {

    private String url = "tls-test.scnetservices.ru";
    private int port = 9000;
    private String writeTestAcc = "{\"id\":2}\n";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSSL() throws Exception {
        InetAddress adr = InetAddress.getByName(url);

        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(adr, port);


        OutputStream outputstream = sslsocket.getOutputStream();
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
        BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
        bufferedwriter.write(writeTestAcc);
        bufferedwriter.flush();

        InputStream inputstream = sslsocket.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

        String string = null;
        string = bufferedreader.readLine();
        AccountDetail accountDetail = objectMapper.readValue(string, AccountDetail.class);
        assertNotNull(accountDetail);
    }



}
