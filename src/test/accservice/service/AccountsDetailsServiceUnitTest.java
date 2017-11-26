package accservice.service;

import accservice.exception.ACCServiceAccountDetailsWithThatIdExistsException;
import accservice.model.AccountDetails;
import accservice.resource.level.db.LevelDBResource;
import accservice.rest.request.AccountDetailsRequest;
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
public class AccountsDetailsServiceUnitTest {

	@Mock
	private LevelDBResource levelDBResource;

	@Mock
	private TlsUriService tlsUriService;


	@InjectMocks
	private AccountsDetailsService accountsDetailsService = new AccountsDetailsService();

	@Test
	public void saveAccountDetailsFoundLocalDB() throws Exception {
		AccountDetailsRequest accountDetailsRequest = mock(AccountDetailsRequest.class);
		when(accountDetailsRequest.getDetails()).thenReturn("saved");
		when(accountDetailsRequest.getId()).thenReturn("1");

		accountsDetailsService.saveAccountDetails(accountDetailsRequest);
		verify(levelDBResource, times(1)).getByKey("1");
		verify(levelDBResource, times(1)).putKeyValue("1", "saved");

		verifyNoMoreInteractions(levelDBResource, tlsUriService);
	}

	@Test
	public void saveAccountDetailsFoundLocalDBException() throws Exception {
		AccountDetailsRequest accountDetailsRequest = mock(AccountDetailsRequest.class);
		when(accountDetailsRequest.getDetails()).thenReturn("saved");
		when(accountDetailsRequest.getId()).thenReturn("1");
		when(levelDBResource.getByKey("1")).thenReturn("something");

		try {
			accountsDetailsService.saveAccountDetails(accountDetailsRequest);
			fail();
		} catch (ACCServiceAccountDetailsWithThatIdExistsException e) {
			assertEquals("AccountDetails with id: 1 already exists", e.getMessage());
		}


		verify(levelDBResource, times(1)).getByKey("1");

		verifyNoMoreInteractions(levelDBResource, tlsUriService);
	}


	@Test
	public void getAccountDetailsByIdFoundLocalDB() throws Exception {
		Long id = 1L;
		when(levelDBResource.getByKey("1")).thenReturn("return_by_key");

		AccountDetails accountDetails = accountsDetailsService.getAccountDetailsById(id);

		assertEquals(id, accountDetails.getId());
		assertEquals("return_by_key", accountDetails.getDetails());

		verify(levelDBResource, times(1)).getByKey("1");
		verifyNoMoreInteractions(levelDBResource, tlsUriService);
	}

	@Test
	public void getAccountDetailsByIdNotFoundLocalDB() throws Exception {
		Long id = 1L;
		AccountDetails accountDetails = mock(AccountDetails.class);
		when(accountDetails.getId()).thenReturn(id);
		when(accountDetails.getDetails()).thenReturn("return_by_key");
		when(levelDBResource.getByKey("1")).thenReturn(null);
		when(tlsUriService.loadAccountDetails("1")).thenReturn(accountDetails);

		AccountDetails accountDetailsResult = accountsDetailsService.getAccountDetailsById(id);

		assertEquals(id, accountDetailsResult.getId());
		assertEquals("return_by_key", accountDetailsResult.getDetails());


		verify(levelDBResource, times(1)).getByKey("1");
		verify(tlsUriService, times(1)).loadAccountDetails("1");
		verify(levelDBResource, times(1)).putKeyValue("1", "return_by_key");

		verifyNoMoreInteractions(levelDBResource, tlsUriService);

	}

	@Test
	public void deleteAccountDetails() throws Exception {
		accountsDetailsService.deleteAccountDetailsById(1L);
		verify(levelDBResource, times(1)).deleteByKey("1");

		verifyNoMoreInteractions(levelDBResource, tlsUriService);
	}


	@Test
	public void updateAccountDetails() throws Exception {
		AccountDetailsRequest accountDetailsRequest = mock(AccountDetailsRequest.class);
		when(accountDetailsRequest.getDetails()).thenReturn("updated");
		when(accountDetailsRequest.getId()).thenReturn("1");

		accountsDetailsService.updateAccountDetails(accountDetailsRequest);
		verify(levelDBResource, times(1)).deleteByKey("1");
		verify(levelDBResource, times(1)).putKeyValue("1", "updated");

		verifyNoMoreInteractions(levelDBResource, tlsUriService);
	}

}