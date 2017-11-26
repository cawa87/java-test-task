package accservice.exception;

public class ACCServiceAccountDetailsWithThatIdExistsException extends ACCServiceException {

	public ACCServiceAccountDetailsWithThatIdExistsException(String message) {
		super(message);
	}

	public ACCServiceAccountDetailsWithThatIdExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
