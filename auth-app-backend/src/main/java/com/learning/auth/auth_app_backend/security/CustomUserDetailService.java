package com.learning.auth.auth_app_backend.security;

import com.learning.auth.auth_app_backend.exceptions.ResourceNotFoundException;
import com.learning.auth.auth_app_backend.respositories.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRespository userRespository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRespository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("Invalid Email"));
    }
}
