package accservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Open a socket.
   Open an input stream and output stream to the socket.
   Read from and write to the stream according to the server's protocol.
   Close the streams.
   Close the socket.
 */
@Configuration
public class SSLConfig {

	private static final String TLSV1_2 = "TLSv1.2";

	@Bean
	public SSLContext ssLContext() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext ctx = SSLContext.getInstance(TLSV1_2);
		ctx.init(null, null, null);
		return ctx;
	}

	@Bean
	public SSLSocketFactory sslSocketFactory(@Autowired SSLContext ssLContext){
		return ssLContext.getSocketFactory();
	}

}
