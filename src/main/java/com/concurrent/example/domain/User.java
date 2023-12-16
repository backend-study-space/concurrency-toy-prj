package com.concurrent.example.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private int age;

    @Enumerated(STRING)
    private UserStatus userStatus;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;

    private User(String userName, String password, int age, Set<Authority> authorities) {
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.userStatus = UserStatus.NORMAL;
        this.authorities = authorities;
    }

    public static User create(String name, String password, int age, Set<Authority> authorities) {
        return new User(name, password, age, authorities);
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

    public boolean isActivated() {
        return this.userStatus == UserStatus.NORMAL;
    }
}
