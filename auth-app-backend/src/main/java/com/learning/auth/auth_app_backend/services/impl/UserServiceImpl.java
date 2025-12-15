package com.learning.auth.auth_app_backend.services.impl;

import com.learning.auth.auth_app_backend.dtos.UserDto;
import com.learning.auth.auth_app_backend.exceptions.ResourceNotFoundException;
import com.learning.auth.auth_app_backend.helpers.UserHelper;
import com.learning.auth.auth_app_backend.model.Provider;
import com.learning.auth.auth_app_backend.model.User;
import com.learning.auth.auth_app_backend.respositories.UserRespository;
import com.learning.auth.auth_app_backend.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRespository userRespository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if(userDto.getEmail()==null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is required");
        }
        if(userRespository.existsByEmail(userDto.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = modelMapper.map(userDto,User.class);
        user.setProvider(userDto.getProvider()!=null ? userDto.getProvider() : Provider.LOCAL);

        User savedUser = userRespository.save(user);
        return modelMapper.map(savedUser,UserDto.class);

    }

    @Override
    public UserDto getUserByEmail(String email) {
    User user =  userRespository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRespository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user,UserDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User user = userRespository.findById(uId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        userRespository.delete(user);
        return;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRespository.findById(UserHelper.parseUUID(userId)).orElseThrow(()->new ResourceNotFoundException("User not found"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User existingUser = userRespository.findById(UserHelper.parseUUID(userId)).orElseThrow(()->new ResourceNotFoundException("User not found"));

        if(userDto.getName()!=null) existingUser.setName(userDto.getName());
        if(userDto.getImage()!=null) existingUser.setImage(userDto.getImage());
        if(userDto.getProvider()!=null) existingUser.setProvider(userDto.getProvider());
        if(userDto.getPassword()!=null) existingUser.setPassword(userDto.getPassword());
        existingUser.setEnable(userDto.isEnable());

        User updatedUser = userRespository.save(existingUser);
        return modelMapper.map(updatedUser,UserDto.class);
    }
}
