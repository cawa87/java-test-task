package accservice.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class TslToolsFactory {

	@Autowired
	private SSLSocketFactory sslSocketFactory;

	public DataOutputStream createDataOutputStream(SSLSocket sslSocket) throws IOException {
		return new DataOutputStream(sslSocket.getOutputStream());
	}

	public BufferedReader createBufferedReader(SSLSocket sslSocket) throws IOException {
		return new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
	}

	public SSLSocket createSocket(String host, Integer port) throws IOException {
		return (SSLSocket) sslSocketFactory.createSocket(host, port);
	}

}
