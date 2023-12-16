package com.concurrent.example.repository;

import com.concurrent.example.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities") // Eager 조회 전용
    Optional<User> findOneWithAuthoritiesByUserName(String userName);
}
