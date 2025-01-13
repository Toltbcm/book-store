package org.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.bookstore.dto.request.UserRegistrationRequestDto;
import org.bookstore.dto.response.UserResponseDto;
import org.bookstore.mapper.UserMapper;
import org.bookstore.model.User;
import org.bookstore.repository.user.UserRepository;
import org.bookstore.service.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        User model = userMapper.toModel(requestDto);
        User save = userRepository.save(model);
        return userMapper.toDto(save);
    }
}
