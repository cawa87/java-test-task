package com.test.repositories;

import com.test.common.enums.Roles;
import com.test.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(Roles role);
}
