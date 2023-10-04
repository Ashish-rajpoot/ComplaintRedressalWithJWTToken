package com.spring.security.services;

import com.spring.security.entity.Role;

import java.util.List;

public interface RoleService {
    Role findByRolename(Role role);
    Role findyByRolename(String role);

    void addRole(Role role);

    List<Role> getRoles();
//    void addRole(String  role);

    Role findById(int id);
}
