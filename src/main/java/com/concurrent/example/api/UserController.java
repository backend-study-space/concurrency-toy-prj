package com.concurrent.example.api;

import com.concurrent.example.api.dto.UserResponse;
import com.concurrent.example.domain.User;
import com.concurrent.example.dto.UserDto;
import com.concurrent.example.repository.UserRepository;
import com.concurrent.example.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;

//    @PostMapping("/user")
//    public User createUser() {
//        return userRepository.save(User.create("곽명환", "1234",28));
//    }

    @PostMapping("signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signUp(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }

//    @GetMapping("/user")
//    public List<User> getUser() {
//        return userRepository.findAll();
//    }

//    @GetMapping("/user/{id}")
//    public UserResponse getUserByUserId(@PathVariable Long id) {
//        return UserResponse.create(userService.getUserByUserId(id));
//    }

    @DeleteMapping("/user/{id}")
    public String deleteUserByUserId(@PathVariable Long id) {
        return userService.deleteUserByUserId(id);
    }
}
