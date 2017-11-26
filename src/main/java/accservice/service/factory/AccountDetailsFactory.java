package accservice.service.factory;

import accservice.model.AccountDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AccountDetailsFactory {

	@Autowired
	private ObjectMapper objectMapper;

	public AccountDetails createAccountDetails(String jsonString){
		try {
			return objectMapper.readValue(jsonString, AccountDetails.class);
		} catch (IOException e) {
			throw new RuntimeException("Can not convert response to AccountDetails: " + jsonString);
		}

	}
}
