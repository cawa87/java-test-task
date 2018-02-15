package com.accounts.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by pasha on 14.02.18.
 */
public class AccountDetail implements Serializable {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String accountDetails;
}
