package com.spring.security.controller;

import com.spring.security.entity.User;
import com.spring.security.servicesimp.UserServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImp userServiceImp;

    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = this.userServiceImp.getUserByEmail(email);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<User> getAllUsers(){
        return this.userServiceImp.findAll();
    }
}
