package com.spring.security.controller;

import com.spring.security.dto.UserDto;
import com.spring.security.entity.Role;
import com.spring.security.entity.User;
import com.spring.security.errors.IncorrectPasswordException;
import com.spring.security.repository.RoleRepository;
import com.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;


    @GetMapping
    public ResponseEntity<String> getMapping() {
        return ResponseEntity.ok("Welcome to Register page");
    }

//    @PostMapping
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        try {
//            if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
//                return ResponseEntity.badRequest().body("Name, email, and password are required.");
//            }
//            if (userRepository.existsByEmail(user.getEmail())) {
//                return ResponseEntity.badRequest().body("User with the provided email already exists.");
//            }
//            Set<Role> roles = user.getRoles();
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user.setRoles(roles);
//
//            // Save the user to the database
//            userRepository.save(user);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
//        } catch (Exception e) {
//            // Handle exceptions, log the error, and return an error response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user.");
//        }
//    }


    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        try {
            // Validate user data (you can add more validation logic here)
            if (userDto.getUsername() == null || userDto.getEmail() == null || userDto.getPassword() == null) {
                return ResponseEntity.badRequest().body("Name, email, and password are required.");
            }

            // Check if the user already exists (e.g., based on email)
            if (userRepository.existsByEmail(userDto.getEmail())) {
                return ResponseEntity.badRequest().body("User with the provided email already exists.");
            }

            Set<Role> roles = userDto.getRoles();
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRoles(roles);

            // Save the user to the database
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (Exception e) {
            // Handle exceptions, log the error, and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user.");
        }
    }

    @PostMapping("/profilePostData/{newpass}")
    public ResponseEntity<String> updateProfiel(@RequestBody UserDto userDto,@PathVariable String newpass) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = this.userRepository.findByEmail(email);
        boolean isMatch = passwordEncoder.matches(userDto.getPassword(), user.getPassword());
        try {
            if (isMatch) {
                user.setEmail(userDto.getEmail());
                user.setUsername(userDto.getUsername());
                user.setPassword(passwordEncoder.encode(newpass));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body("Profile Updated");
            } else {
                throw new IncorrectPasswordException("Incorrect Current Password!!!");
            }

        } catch (IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
