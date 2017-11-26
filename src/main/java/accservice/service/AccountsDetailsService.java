package accservice.service;

import accservice.exception.ACCServiceAccountDetailsWithThatIdExistsException;
import accservice.exception.ACCServiceObjectNotFoundException;
import accservice.exception.ACCServiceOutOfServiceException;
import accservice.model.AccountDetails;
import accservice.resource.level.db.LevelDBResource;
import accservice.rest.request.AccountDetailsRequest;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AccountsDetailsService {

	@Autowired
	private LevelDBResource levelDBResource;

	@Autowired
	private TlsUriService tlsUriService;

	public void saveAccountDetails(AccountDetailsRequest accountDetails) {
		try {
			String accountDetailsFromDatabase = levelDBResource.getByKey(String.valueOf(accountDetails.getId()));
			if (accountDetailsFromDatabase != null) {
				throw new ACCServiceAccountDetailsWithThatIdExistsException("AccountDetails with id: "
						+ accountDetails.getId() + " already exists");
			}

			putKeyValue(String.valueOf(accountDetails.getId()), accountDetails.getDetails());

		} catch (IOException e) {
			throw new ACCServiceOutOfServiceException("Can no perform saveAccountDetails application broken", e);
		}
	}

	public AccountDetails getAccountDetailsById(Long id) {
		try {
			String details = levelDBResource.getByKey(String.valueOf(id));
			if (Strings.isNullOrEmpty(details)) {
				return loadFromTslServiceIfExists(id);
			}
			return new AccountDetails(id, details);
		} catch (IOException e) {
			throw new ACCServiceOutOfServiceException("Can no perform getAccountDetailsById application broken", e);
		}
	}

	private AccountDetails loadFromTslServiceIfExists(Long id) {
		AccountDetails accountDetails = tlsUriService.loadAccountDetails(String.valueOf(id));
		if (accountDetails == null) {
			throw new ACCServiceObjectNotFoundException("Not object found with provided id: " + id);
		} else {
			try {
				levelDBResource.putKeyValue(String.valueOf(accountDetails.getId()),accountDetails.getDetails());
			} catch (IOException e) {
				throw new ACCServiceOutOfServiceException("LocalDB operation not possible" , e);
			}
			return accountDetails;
		}
	}

	public void deleteAccountDetailsById(Long id) {
		try {
			levelDBResource.deleteByKey(String.valueOf(id));
		} catch (IOException e) {
			throw new ACCServiceOutOfServiceException("Can no perform getAccountDetailsById application broken", e);
		}
	}

	/**
	 * Oracle Style update, if not exists = create
	 */
	public void updateAccountDetails(AccountDetailsRequest accountDetailsRequest) {
		try {
			levelDBResource.deleteByKey(String.valueOf(accountDetailsRequest.getId()));
			putKeyValue(accountDetailsRequest.getId(), accountDetailsRequest.getDetails());
		} catch (IOException e) {
			throw new RuntimeException("Can no perform getAccountDetailsById application broken", e);
		}
	}

	private void putKeyValue(String key, String value) throws IOException {
		levelDBResource.putKeyValue(String.valueOf(key), value);
	}


}
