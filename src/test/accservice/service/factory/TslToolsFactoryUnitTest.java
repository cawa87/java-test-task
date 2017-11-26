package accservice.service.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TslToolsFactoryUnitTest {

	@Mock
	private SSLSocketFactory sslSocketFactory;

	@InjectMocks
	private TslToolsFactory tslToolsFactory = new TslToolsFactory();

	@Test
	public void createSocket() throws Exception {
		SSLSocket sslSocket = mock(SSLSocket.class);
		when(sslSocketFactory.createSocket("test-host", 1234)).thenReturn(sslSocket);
		assertEquals(sslSocket, tslToolsFactory.createSocket("test-host", 1234));
	}

}