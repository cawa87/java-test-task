package com.example.repository;

import com.example.model.AccountDetailsResponse;

import java.io.IOException;

/**
 * @author dasolovyov on 23.10.2017.
 */
public interface AccountDetailsRepositoty {
    AccountDetailsResponse get(Integer id) throws IOException;
}
