package com.learning.auth.auth_app_backend.services.impl;

import com.learning.auth.auth_app_backend.dtos.UserDto;
import com.learning.auth.auth_app_backend.services.AuthService;
import com.learning.auth.auth_app_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    @Override
    public UserDto regsiterUser(UserDto userDto) {
        return null;
    }
}
