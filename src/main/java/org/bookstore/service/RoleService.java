package org.bookstore.service;

import org.bookstore.model.Role;

public interface RoleService {

    Role geRoletByName(Role.RoleName name);
}
