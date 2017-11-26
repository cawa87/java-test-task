package accservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDetails {

	private Long id;

	@JsonProperty("accountDetails")
	private String details;

	public AccountDetails() {
	}

	public AccountDetails(Long id, String details) {
		this.id = id;
		this.details = details;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
