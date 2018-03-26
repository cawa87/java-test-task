package com.test.services.impl;

import com.test.common.enums.Roles;
import com.test.entities.Role;
import com.test.repositories.RoleRepository;
import com.test.services.RoleService;
import com.test.services.base.impl.DefaultBaseReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRoleService extends DefaultBaseReadService<Role> implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    protected DefaultRoleService(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getByName(String roleName) {
        return roleRepository.findByName(Roles.valueOf(roleName));
    }
}
