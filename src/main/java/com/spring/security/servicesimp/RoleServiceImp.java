package com.spring.security.servicesimp;

import com.spring.security.entity.Role;
import com.spring.security.repository.RoleRepository;
import com.spring.security.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    public Role findByRolename(Role role) {
        return roleRepository.findByRolename(role) ;
    }

    @Override
    public Role findyByRolename(String role) {
        return roleRepository.findByRolename(role);
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(int id) {
        return roleRepository.findById(id).orElseThrow(()-> new RuntimeException("No role Found"));
    }

//    @Override
//    public void addRole(String role) {
//        roleRepository.save(role);
//    }
}
