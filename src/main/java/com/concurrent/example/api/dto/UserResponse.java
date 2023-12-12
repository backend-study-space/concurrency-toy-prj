package com.concurrent.example.api.dto;

import com.concurrent.example.domain.User;
import lombok.Data;

@Data
public class UserResponse {
    private String name;
    private int age;

    private UserResponse(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static UserResponse create(User user) {
        return new UserResponse(user.getName(), user.getAge());
    }
}
