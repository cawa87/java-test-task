package io.ronte.service;

import io.ronte.model.AccountDetailsResponse;

public interface AccountDetailsService {
    AccountDetailsResponse getAccountDetails(long id);
}
