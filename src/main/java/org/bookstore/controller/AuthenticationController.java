package org.bookstore.controller;

import org.bookstore.dto.UserRegistrationRequestDto;
import org.bookstore.dto.UserResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @PostMapping("/register")
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        return null;
    }
}
