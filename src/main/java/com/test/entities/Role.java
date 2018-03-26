package com.test.entities;

import com.test.common.enums.Roles;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(name = "name", unique = true)
    @Enumerated(EnumType.STRING)
    private Roles name;

    public Roles getName() {
        return name;
    }

    public void setName(Roles name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}
