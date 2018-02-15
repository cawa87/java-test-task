package com.accounts.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by pasha on 14.02.18.
 */
@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Transient
    @Getter
    @Setter
    private AccountDetail accountDetail;

}
