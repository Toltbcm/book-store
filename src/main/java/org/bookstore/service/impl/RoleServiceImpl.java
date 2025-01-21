package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.exception.EntityNotFoundException;
import org.bookstore.model.Role;
import org.bookstore.repository.role.RoleRepository;
import org.bookstore.service.RoleService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getByName(Role.RoleName name) {
        return roleRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Can't find role with name: " + name));
    }
}
