package accservice.rest.request;

public class AccountDetailsRequest {

	private String id;

	private String details;

	private AuthRequest auth;

	public AccountDetailsRequest() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AuthRequest getAuth() {
		return auth;
	}

	public void setAuth(AuthRequest auth) {
		this.auth = auth;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
