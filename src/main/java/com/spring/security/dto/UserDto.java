package com.spring.security.dto;

import com.spring.security.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
    // Other properties as needed
}

