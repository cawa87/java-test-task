package com.example.controller;

import com.example.model.AccountDetailsResponse;
import com.example.repository.AccountDetailsRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author dasolovyov on 23.10.2017.
 */
@RestController
public class AccountDetailsController {
    @Autowired
    private AccountDetailsRepositoty accountDetailsRepositoty;

    @RequestMapping("/accountDetails")
    public AccountDetailsResponse get(@Param("id") Integer id) throws IOException {
        return accountDetailsRepositoty.get(id);
    }
}
