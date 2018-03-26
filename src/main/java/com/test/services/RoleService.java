package com.test.services;

import com.test.entities.Role;
import com.test.services.base.BaseReadService;

public interface RoleService extends BaseReadService<Role> {

    Role getByName(String roleName);
}
