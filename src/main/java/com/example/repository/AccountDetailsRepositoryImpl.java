package com.example.repository;

import com.example.model.AccountDetailsRequest;
import com.example.model.AccountDetailsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author dasolovyov on 23.10.2017.
 */
@Repository
public class AccountDetailsRepositoryImpl implements AccountDetailsRepositoty {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public AccountDetailsResponse get(Integer id) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket socket = factory.createSocket("tls-test.scnetservices.ru", 9000);

        OutputStream theOutput = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(theOutput, false);
        // Native line endings are uncertain so add them manually
        pw.print(mapper.writeValueAsString(new AccountDetailsRequest(id)));
        pw.print("\r\n");
        pw.flush();

        AccountDetailsResponse result = mapper.readValue(socket.getInputStream(), AccountDetailsResponse.class);
        socket.close();

        return result;
    }
}
