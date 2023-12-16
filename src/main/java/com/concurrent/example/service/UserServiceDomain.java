package com.concurrent.example.service;

import com.concurrent.example.domain.Authority;
import com.concurrent.example.domain.User;
import com.concurrent.example.dto.UserDto;
import com.concurrent.example.repository.UserRepository;
import com.concurrent.example.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.concurrent.example.domain.User.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceDomain implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Executor executor;

    @Override
    public User signUp(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUserName(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.create("ROLE_USER");

        return userRepository.save(
                User.create(
                        userDto.getUsername(),
                        passwordEncoder.encode(userDto.getPassword()),
                        userDto.getAge(),
                        Collections.singleton(authority)
                )
        );
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUserName(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUserName)
                        .orElseThrow(() -> new RuntimeException("Member not found"))
        );
    }

//    @Override
//    @Transactional(readOnly = true)
//    public User getUserByUserId(Long id) {
//        return findUser(userRepository.findAll(), id);
//    }

    @Override
    public String deleteUserByUserId(Long id) {
        CompletableFuture.supplyAsync(() -> {
            User user = findUser(userRepository.findAll(), id);
            log.info(Thread.currentThread().getName() + " : 유저 반환 완료");
            return user;
        }, executor).thenAcceptAsync(r -> {
            try {
                Thread.sleep(5000);
                r.deleteUser();
                log.info(Thread.currentThread().getName() + " : 찐 삭제 완료");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        log.info(Thread.currentThread().getName() + " : 삭제 완료");

        return "삭제 완료";
    }

}
