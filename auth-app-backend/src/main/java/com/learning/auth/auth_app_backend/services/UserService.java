package com.learning.auth.auth_app_backend.services;

import com.learning.auth.auth_app_backend.dtos.UserDto;

public interface UserService {

    // create user
    UserDto createUser(UserDto userDto);

    // get user by email
    UserDto getUserByEmail(String email);

    //get all user
    Iterable<UserDto> getAllUsers();

    // delete the user
    void deleteUser(String userId);

    // get user by id
    UserDto getUserById(String userId);

    // update the user
    UserDto updateUser(UserDto userDto,String userId);

}
