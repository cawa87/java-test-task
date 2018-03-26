package com.test.entities;

import javax.persistence.*;

@MappedSuperclass
abstract public class BaseEntity {

    @Id
    @SequenceGenerator(name = "sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
