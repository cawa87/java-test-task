package accservice.rest.controller;

import accservice.rest.response.Response;

public abstract class Controller {

	static final String ACCEPT_APPLICATION_JSON = "Accept=application/json";

	Response createResponse(String id) {
		return new Response("OK. with id: " + id);
	}

}
