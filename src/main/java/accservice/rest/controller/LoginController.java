package accservice.rest.controller;

import accservice.rest.request.AddLoginAccountRequest;
import accservice.rest.response.Response;
import accservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController  extends Controller {

	@Autowired
	private LoginService loginService;

	@PutMapping(value = "/put/login/token", headers = {ACCEPT_APPLICATION_JSON})
	@ResponseBody
	public Response putAccountDetails(@RequestBody AddLoginAccountRequest addLoginAccountRequest){
		loginService.saveLogin(addLoginAccountRequest);
		return createResponse(addLoginAccountRequest.getLogin() + " is saved");
	}
}
