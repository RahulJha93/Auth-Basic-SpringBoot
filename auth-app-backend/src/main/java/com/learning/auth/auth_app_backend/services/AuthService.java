package com.learning.auth.auth_app_backend.services;

import com.learning.auth.auth_app_backend.dtos.LoginRequest;
import com.learning.auth.auth_app_backend.dtos.TokenResponse;
import com.learning.auth.auth_app_backend.dtos.UserDto;

public interface AuthService {
    UserDto registerUser(UserDto userDto);
    TokenResponse login(LoginRequest loginRequest);
}
