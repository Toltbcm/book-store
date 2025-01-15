package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.UserRegistrationRequestDto;
import org.bookstore.dto.response.UserResponseDto;
import org.bookstore.exception.RegistrationException;
import org.bookstore.mapper.UserMapper;
import org.bookstore.repository.role.RoleRepository;
import org.bookstore.repository.user.UserRepository;
import org.bookstore.service.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.email()).isPresent()) {
            throw new RegistrationException("Can't register user");
        }
        return userMapper.toDto(userRepository.save(
                userMapper.toModelWithUserRole(requestDto, roleRepository)));
    }
}
