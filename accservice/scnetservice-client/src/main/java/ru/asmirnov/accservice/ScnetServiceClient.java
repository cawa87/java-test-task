package ru.asmirnov.accservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.asmirnov.accservice.config.ScnetServiceClientProperties;
import ru.asmirnov.accservice.dto.AccountDetailsRequestDto;
import ru.asmirnov.accservice.dto.AccountDetailsResponseDto;
import ru.asmirnov.accservice.exception.ScnetServiceClientException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * @author Alexey Smirnov at 28/03/2018
 */
@Component
public class ScnetServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(ScnetServiceClient.class);
    public static final String LINE_SEPARATOR = "\r\n";

    private final ScnetServiceClientProperties properties;
    private final ObjectMapper objectMapper;

    public ScnetServiceClient(ScnetServiceClientProperties properties,
                              ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;

        logger.info("ScnetServiceClient initialized with properties: {}", properties);
    }

    public AccountDetailsResponseDto getAccountDetails(AccountDetailsRequestDto accountDetailsRequestDto) throws ScnetServiceClientException {
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(properties.getUrl(), properties.getPort())) {
            return sendRequest(sslsocket, accountDetailsRequestDto);
        } catch (IOException e) {
            throw new ScnetServiceClientException("Error in get account details", e);
        }
    }

    private AccountDetailsResponseDto sendRequest(SSLSocket sslsocket, AccountDetailsRequestDto accountDetailsRequestDto) throws IOException {
        InputStream inputstream = sslsocket.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

        OutputStream outputstream = sslsocket.getOutputStream();
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
        BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

        bufferedwriter.write(objectMapper.writeValueAsString(accountDetailsRequestDto));
        bufferedwriter.write(LINE_SEPARATOR);
        bufferedwriter.flush();

        String line = bufferedreader.readLine();
        return objectMapper.readValue(line, AccountDetailsResponseDto.class);
    }
}
