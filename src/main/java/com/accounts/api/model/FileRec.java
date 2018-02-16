package com.accounts.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by pasha on 15.02.18.
 */
@Entity
public class FileRec implements Serializable {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Type(type="org.hibernate.type.BinaryType")
    @Column(length = 10000000)
    private byte[] data;

}
