package accservice.service;

import accservice.model.AccountDetails;
import accservice.service.factory.AccountDetailsFactory;
import accservice.service.factory.TslToolsFactory;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

@Service
public class TlsUriService {

	@Value("${ssl.host}")
	private String host;

	@Value("${ssl.port}")
	private String port;

	@Autowired
	private AccountDetailsFactory accountDetailsFactory;

	@Autowired
	private TslToolsFactory tslToolsFactory;

	public AccountDetails loadAccountDetails(String id) {
		String message;
		try {
			SSLSocket socket = tslToolsFactory.createSocket(host, Integer.parseInt(port));
			DataOutputStream dOut = tslToolsFactory.createDataOutputStream(socket);
			BufferedReader inStream = tslToolsFactory.createBufferedReader(socket);
			dOut.writeBytes(createJSONQueryString(id));
			dOut.flush();
			message = inStream.readLine();
			dOut.close();
			inStream.close();
			socket.close();
		} catch (IOException e) {
			throw new RuntimeException("Couldn't get I/O for the connection to: " + host);
		}
		return accountDetailsFactory.createAccountDetails(message);
	}

	@VisibleForTesting
	String createJSONQueryString(String id) {
		return "{\"id\":[ID]}\r\n".replace("[ID]", id);
	}


}
