package com.ronte.api.util;

import org.springframework.stereotype.Service;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

@Service
public class SSLClient {

    public String sendRequest(String host, int port, String message) {
        SocketFactory sslSocketFactory = SSLSocketFactory.getDefault();
        try (Socket sslSocket = sslSocketFactory.createSocket(host, port)) {
            DataOutputStream outputStream = new DataOutputStream(sslSocket.getOutputStream());
            outputStream.writeBytes(message + "\r\n");

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            return inputStream.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
