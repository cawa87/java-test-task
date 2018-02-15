package com.accounts;

import org.junit.Test;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.*;

/**
 * Created by pasha on 14.02.18.
 */
public class SSLConnectTest {

    private String url = "tls-test.scnetservices.ru";
    private int port = 9000;
    private String writeTestAcc = "{\"id\":2}\n";

    @Test
    public void testSSL() throws IOException {
        InetAddress adr = InetAddress.getByName(url);
        Socket clientSocket = SSLSocketFactory.getDefault().createSocket(adr, port);

        ByteBuffer sendBuffer = ByteBuffer.allocate(10000);
        sendBuffer.put(writeTestAcc.getBytes());

        clientSocket.getOutputStream().write(sendBuffer.array());
        System.out.println(clientSocket.isConnected());

        byte[] receiveBuffer = new byte[100000];

        while (clientSocket.getInputStream().read(receiveBuffer) != -1) {
            System.out.print(new String(receiveBuffer));
        }

    }

    @Test
    public void testSSL2() throws Exception {
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
        System.out.println(string);
    }

}
