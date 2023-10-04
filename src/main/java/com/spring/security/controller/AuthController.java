package com.spring.security.controller;

import com.spring.security.config.UserInfoDetailService;
import com.spring.security.entity.JwtRequest;
import com.spring.security.entity.JwtResponse;
import com.spring.security.entity.User;
import com.spring.security.services.UserService;
import com.spring.security.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    JWTUtility jwtUtility;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserInfoDetailService userInfoDetailService;
    @Autowired
    UserService userService;

    @GetMapping
    public String homeRoute(){
        return "Home Route";
    }

//    @PostMapping
//    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        jwtRequest.getEmail(),
//                        jwtRequest.getPassword()
//                )
//        );
//        if(authentication.isAuthenticated()){
//            UserDetails userDetails = userInfoDetailService.loadUserByUsername(jwtRequest.getEmail());
//            String token = jwtUtility.generateToken(userDetails);
//            return new JwtResponse(token);
//        }else {
//            throw new UsernameNotFoundException("user Not Found");
//        }
//    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getEmail(),
                            jwtRequest.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = userInfoDetailService.loadUserByUsername(jwtRequest.getEmail());
                String token = jwtUtility.generateToken(userDetails);
                System.out.println(token);
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
            }
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + ex.getMessage());
        }
    }

    @GetMapping("/{token}")
    public ResponseEntity<User> getUserByToken(@PathVariable String token){
        String  username = jwtUtility.getUsernameFromToken(token);
        User user = userService.getUserByEmail(username);
        return  ResponseEntity.ok(user);
    }

//    @GetMapping("/token/{token}")
//    public boolean isTokenExpire(@PathVariable String token){
//        Boolean check = this.jwtUtility.isTokenExpired(token);
//            = this.jwtUtility.getExpirationDateFromToken(token);
//        return check;
//    }
    @GetMapping("/validate-token/{token}")
    public ResponseEntity<?> validateToken(@PathVariable String token) {
        String userName= jwtUtility.getUsernameFromToken(token);
        UserDetails userDetails = userInfoDetailService.loadUserByUsername(userName);
        boolean isValid = jwtUtility.validateToken(token, userDetails); // Assuming UserDetails is not needed here
        if (isValid) {
            return ResponseEntity.status(HttpStatus.OK).body(isValid);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
    }

    @GetMapping("/principle")
    public User  getPrinciple(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println(email);
        return this.userService.getUserByEmail(email);
    }
//    @GetMapping("/principle")
//    public User getPrinciple(){
//        return this.userService.getUserByEmail(this.getPrinciple().getEmail());
//    }

}
