package com.concurrent.example.api;

import com.concurrent.example.api.dto.UserResponse;
import com.concurrent.example.domain.User;
import com.concurrent.example.repository.UserRepository;
import com.concurrent.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;

    @PostMapping("/user")
    public User createUser() {
        return userRepository.save(User.create("곽명환", 28));
    }

    @GetMapping("/user")
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public UserResponse getUserByUserId(@PathVariable Long id) {
        return UserResponse.create(userService.getUserByUserId(id));
    }

    @DeleteMapping("/user/{id}")
    public String deleteUserByUserId(@PathVariable Long id) {
        return userService.deleteUserByUserId(id);
    }
}
