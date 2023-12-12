package com.concurrent.example.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    @Enumerated(STRING)
    private UserStatus userStatus;

    private User(String name, int age) {
        this.name = name;
        this.age = age;
        this.userStatus = UserStatus.NORMAL;
    }

    public static User create(String name, int age) {
        return new User(name, age);
    }

    public static User findUser(List<User> users, Long id) {
        return users.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst()
                .orElseThrow(() -> {
                    throw new RuntimeException("error");
                });
    }

    public void deleteUser() {
        this.userStatus = UserStatus.DELETE;
    }

    private enum UserStatus {
        NORMAL,
        DELETE
    }
}
