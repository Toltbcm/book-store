package org.bookstore.service;

import org.bookstore.model.Role;

public interface RoleService {

    Role geRoleByName(Role.RoleName name);
}
