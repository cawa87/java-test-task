package accservice.rest.controller;


import accservice.model.AccountDetails;
import accservice.rest.request.AccountDetailsRequest;
import accservice.rest.response.Response;
import accservice.service.AccountsDetailsService;
import accservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController extends Controller {

	@Autowired
	private AccountsDetailsService accountsDetailsService;

	@Autowired
	private LoginService loginService;

	@PutMapping(value = "/get/id", headers = {ACCEPT_APPLICATION_JSON})
	@ResponseBody
	public AccountDetails getAccountDetailsById(@RequestBody AccountDetailsRequest accountDetailsRequest){
		loginService.auth(accountDetailsRequest.getAuth());

		return accountsDetailsService.getAccountDetailsById(Long.valueOf(accountDetailsRequest.getId()));
	}

	@PutMapping(value = "/delete/id", headers = {ACCEPT_APPLICATION_JSON})
	@ResponseBody
	public Response deleteAccountById(@RequestBody AccountDetailsRequest accountDetailsRequest){
		loginService.auth(accountDetailsRequest.getAuth());

		final String id = accountDetailsRequest.getId();

		accountsDetailsService.deleteAccountDetailsById(Long.valueOf(id));
		return createResponse("OK. AccountDetailsWithId: " + id + " is deleted");
	}

	@PutMapping(value = "/update/id/details", headers = {ACCEPT_APPLICATION_JSON})
	@ResponseBody
	public Response updateAccountDetailsById(@RequestBody AccountDetailsRequest accountDetailsRequest){
		loginService.auth(accountDetailsRequest.getAuth());

		accountsDetailsService.updateAccountDetails(accountDetailsRequest);
		return createResponse(accountDetailsRequest.getId() + " is updated");
	}


	@PutMapping(value = "/put/id/details", headers = {ACCEPT_APPLICATION_JSON})
	@ResponseBody
	public Response putAccountDetails(@RequestBody AccountDetailsRequest accountDetails){
		loginService.auth(accountDetails.getAuth());

		accountsDetailsService.saveAccountDetails(accountDetails);
		return createResponse(accountDetails.getId() + " is saved");
	}


}
