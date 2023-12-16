package com.concurrent.example.service;

import com.concurrent.example.domain.User;
import com.concurrent.example.dto.UserDto;

import java.util.Optional;

public interface UserService {
//    User getUserByUserId(Long id);

    String deleteUserByUserId(Long id);

    User signUp(UserDto userDto);

    UserDto getUserWithAuthorities(String username);

    UserDto getMyUserWithAuthorities();
}
