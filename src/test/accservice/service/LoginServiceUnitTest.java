package accservice.service;

import accservice.exception.ACCServiceAuthFailedException;
import accservice.resource.level.db.LevelDBResource;
import accservice.rest.request.AddLoginAccountRequest;
import accservice.rest.request.AuthRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceUnitTest {

	@Mock
	private LevelDBResource levelDBResource;

	@InjectMocks
	private LoginService loginService = new LoginService();

	@Test
	public void saveLogin() throws Exception {
		AddLoginAccountRequest addLoginAccountRequest = mock(AddLoginAccountRequest.class);
		when(addLoginAccountRequest.getLogin()).thenReturn("sLogin");
		when(addLoginAccountRequest.getToken()).thenReturn("sTocken");

		loginService.saveLogin(addLoginAccountRequest);
		verify(levelDBResource, times(1)).putKeyValue("sLogin", "sTocken");

		verifyNoMoreInteractions(levelDBResource);

	}

	@Test
	public void auth() throws Exception {
		AuthRequest authRequest = mock(AuthRequest.class);
		when(authRequest.getLogin()).thenReturn("login");
		when(authRequest.getToken()).thenReturn("token");
		when(levelDBResource.getByKey("login")).thenReturn("token");
		try {
			loginService.auth(authRequest);
		} catch (ACCServiceAuthFailedException e) {
			fail();
		}
		verify(levelDBResource, times(1)).getByKey("login");
		verifyNoMoreInteractions(levelDBResource);
	}

	@Test
	public void authException() throws Exception {
		AuthRequest authRequest = mock(AuthRequest.class);
		when(authRequest.getLogin()).thenReturn("login");
		when(authRequest.getToken()).thenReturn("token");
		when(levelDBResource.getByKey("login")).thenReturn("something_other");
		try {
			loginService.auth(authRequest);
			fail();
		} catch (ACCServiceAuthFailedException e) {
			assertEquals("Failed auth with login: login", e.getMessage());
		}
		verify(levelDBResource, times(1)).getByKey("login");
		verifyNoMoreInteractions(levelDBResource);
	}

}