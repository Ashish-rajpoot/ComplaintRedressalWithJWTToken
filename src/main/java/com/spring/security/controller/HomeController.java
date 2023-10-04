package com.spring.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/")
public class HomeController {

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMI')")
    public String home() {
        return "Welcome to Home Controller";
    }
}
