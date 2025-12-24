package com.learning.auth.auth_app_backend.services.impl;

import com.learning.auth.auth_app_backend.dtos.LoginRequest;
import com.learning.auth.auth_app_backend.dtos.TokenResponse;
import com.learning.auth.auth_app_backend.dtos.UserDto;
import com.learning.auth.auth_app_backend.exceptions.ResourceNotFoundException;
import com.learning.auth.auth_app_backend.model.User;
import com.learning.auth.auth_app_backend.respositories.UserRespository;
import com.learning.auth.auth_app_backend.security.JwtServices;
import com.learning.auth.auth_app_backend.services.AuthService;
import com.learning.auth.auth_app_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final UserService userService;
    private final UserRespository userRepository;
    private final JwtServices jwtServices;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDto registerUser(UserDto userDto) {
        // Hash the password before saving
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userService.createUser(userDto);
    }
    
    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.password()
                    )
            );
            
            // Get user from database
            User user = userRepository.findByEmail(loginRequest.email())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            
            // Generate tokens
            String jti = UUID.randomUUID().toString();
            String accessToken = jwtServices.generateAccessToken(user);
            String refreshToken = jwtServices.generateRefreshToken(user, jti);
            
            // Convert user to DTO
            UserDto userDto = modelMapper.map(user, UserDto.class);
            
            return TokenResponse.bearer(
                    accessToken,
                    refreshToken,
                    3600L, // 1 hour
                    userDto
            );
            
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
