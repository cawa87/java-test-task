package accservice.service;

import accservice.service.factory.AccountDetailsFactory;
import accservice.service.factory.TslToolsFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TlsUriServiceUnitTest {

	@Mock
	private AccountDetailsFactory accountDetailsFactory;

	@Mock
	private TslToolsFactory tslToolsFactory;

	@InjectMocks
	private TlsUriService tlsUriService = new TlsUriService();

	@Before
	public void setUp() throws Exception {
		Whitebox.setInternalState(tlsUriService, "host", "test-host");
		Whitebox.setInternalState(tlsUriService, "port", "1234");
	}

	@Test
	public void loadAccountDetails() throws Exception {
		SSLSocket sslSocket = mock(SSLSocket.class);
		OutputStream outputStream = mock(OutputStream.class);
		InputStream inputStream = mock(InputStream.class);

		DataOutputStream dataOutputStream = mock(DataOutputStream.class);
		BufferedReader bufferedReader = mock(BufferedReader.class);

		Whitebox.setInternalState(dataOutputStream, "out", mock(OutputStream.class));

		when(sslSocket.getOutputStream()).thenReturn(outputStream);
		when(sslSocket.getInputStream()).thenReturn(inputStream);

		when(tslToolsFactory.createSocket("test-host", 1234)).thenReturn(sslSocket);
		when(tslToolsFactory.createDataOutputStream(sslSocket)).thenReturn(dataOutputStream);
		when(tslToolsFactory.createBufferedReader(sslSocket)).thenReturn(bufferedReader);

		tlsUriService.loadAccountDetails("3");

		verify(tslToolsFactory, times(1))
				.createSocket("test-host", 1234);

		verify(tslToolsFactory, times(1)).createDataOutputStream(sslSocket);
		verify(tslToolsFactory, times(1)).createBufferedReader(sslSocket);

		verify(dataOutputStream, times(1)).flush();
		verify(bufferedReader, times(1)).readLine();
		verify(dataOutputStream, times(1)).close();
		verify(bufferedReader, times(1)).close();
		verify(sslSocket, times(1)).close();

		verifyNoMoreInteractions(sslSocket, outputStream,
				inputStream, dataOutputStream, bufferedReader, tslToolsFactory);


	}

	@Test
	public void loadAccountDetailsException() throws Exception {

		when(tslToolsFactory.createSocket("test-host", 1234)).thenThrow(new IOException("WRONG THROW"));
		try {
			tlsUriService.loadAccountDetails("3");
			fail();
		} catch (RuntimeException e) {
			assertEquals("Couldn't get I/O for the connection to: test-host", e.getMessage());
		}
	}


	@Test
	public void createJSONQueryString() throws Exception {
		assertEquals("{\"id\":3}\r\n", tlsUriService.createJSONQueryString("3"));
		assertEquals("{\"id\":1}\r\n", tlsUriService.createJSONQueryString("1"));
	}

}