package com.concurrent.example.service;

import com.concurrent.example.domain.User;

public interface UserService {
    User getUserByUserId(Long id);

    String deleteUserByUserId(Long id);
}
