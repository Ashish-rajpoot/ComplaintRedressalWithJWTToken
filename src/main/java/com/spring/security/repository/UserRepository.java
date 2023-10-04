package com.spring.security.repository;

import com.spring.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String user);
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
