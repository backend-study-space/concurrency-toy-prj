package com.concurrent.example.service;

import com.concurrent.example.domain.User;
import com.concurrent.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.concurrent.example.domain.User.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceDomain implements UserService {

    private final UserRepository userRepository;

    private final Executor executor;

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserId(Long id) {
        return findUser(userRepository.findAll(), id);
    }

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
