package com.spring.security.services;

import com.spring.security.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> getUser(String user);

    void deleteUser(long id);

    void updateUser(User user);
    boolean existsByEmail(String email);
    User getUserByEmail(String email);

    User findUserById(int id);

    List<User> findAll();
}
