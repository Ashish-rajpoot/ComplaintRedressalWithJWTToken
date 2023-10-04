package com.spring.security.servicesimp;

import com.spring.security.entity.User;
import com.spring.security.repository.UserRepository;
import com.spring.security.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getUser(String user) {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById((int) id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("user Not Found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
