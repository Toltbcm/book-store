package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.UserRegistrationRequestDto;
import org.bookstore.dto.response.UserResponseDto;
import org.bookstore.exception.RegistrationException;
import org.bookstore.mapper.UserMapper;
import org.bookstore.model.ShoppingCart;
import org.bookstore.model.User;
import org.bookstore.repository.user.UserRepository;
import org.bookstore.service.RoleService;
import org.bookstore.service.ShoppingCartService;
import org.bookstore.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException("Can't register user by email: " + requestDto.email());
        }
        User user = userRepository.save(userMapper.toModelWithUserRole(requestDto, roleService));
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartService.create(shoppingCart);
        return userMapper.toDto(user);
    }
}
