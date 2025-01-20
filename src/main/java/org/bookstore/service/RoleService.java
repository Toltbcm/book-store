package org.bookstore.service;

import org.bookstore.model.Role;

public interface RoleService {

    Role getByName(Role.RoleName name);
}
