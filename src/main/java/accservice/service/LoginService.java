package accservice.service;

import accservice.exception.ACCServiceAuthFailedException;
import accservice.exception.ACCServiceOutOfServiceException;
import accservice.resource.level.db.LevelDBResource;
import accservice.rest.request.AddLoginAccountRequest;
import accservice.rest.request.AuthRequest;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginService {

	@Autowired
	private LevelDBResource levelDBResource;

	public void saveLogin(AddLoginAccountRequest addLoginAccountRequest) {
		try {
			levelDBResource.putKeyValue(addLoginAccountRequest.getLogin(), addLoginAccountRequest.getToken());
		} catch (IOException e) {
			throw new ACCServiceOutOfServiceException("Database operation not possible: ", e);
		}
	}

	public void auth(AuthRequest authRequest) {
		try {
			final String login = authRequest.getLogin();
			String token = levelDBResource.getByKey(login);
			if (Strings.isNullOrEmpty(token) || !token.equals(authRequest.getToken())) {
				throw new ACCServiceAuthFailedException("Failed auth with login: "
						+ login);
			}
		} catch (IOException e) {
			throw new ACCServiceOutOfServiceException("Database operation not possible: ", e);
		}
	}
}
