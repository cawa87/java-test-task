package accservice.exception;

public abstract class ACCServiceException extends RuntimeException {
	public ACCServiceException(String message) {
		super(message);
	}

	public ACCServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
