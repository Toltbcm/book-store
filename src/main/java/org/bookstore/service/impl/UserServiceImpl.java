package org.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.UserRegistrationRequestDto;
import org.bookstore.dto.response.UserResponseDto;
import org.bookstore.exception.RegistrationException;
import org.bookstore.mapper.UserMapper;
import org.bookstore.model.Role;
import org.bookstore.model.ShoppingCart;
import org.bookstore.model.User;
import org.bookstore.repository.UserRepository;
import org.bookstore.service.RoleService;
import org.bookstore.service.ShoppingCartService;
import org.bookstore.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException("Can't register user by email: " + requestDto.email());
        }
        User user = userRepository.save(userMapper.toModelWithRoles(
                requestDto,
                List.of(roleService.getRoleByName(Role.RoleName.USER)),
                passwordEncoder));
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartService.save(shoppingCart);
        return userMapper.toDto(user);
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Can't find user by email: " + email));
    }
}
