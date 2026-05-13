package com.harsh.myfirstapp.repository;

import com.harsh.myfirstapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Boot apne aap "SELECT * FROM users WHERE username = ?" wali query bana dega
    Optional<User> findByUsername(String username);
}
