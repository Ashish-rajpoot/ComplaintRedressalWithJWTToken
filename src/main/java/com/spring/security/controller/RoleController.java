package com.spring.security.controller;

import com.spring.security.entity.Role;
import com.spring.security.servicesimp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleServiceImp roleServiceImp;

    @GetMapping()
    public List<Role> getAllRoles(){
        return this.roleServiceImp.getRoles();
    }
    @PostMapping
    public ResponseEntity<?> addRoles(@RequestBody Role req){
//        Role role = new Role();
//        role.setRolename(role.getRolename());
        roleServiceImp.addRole(req);
    return ResponseEntity.ok().body("Role added");
    }

    @GetMapping("/{rolename}")
    public Role getRoleByName(@PathVariable String rolename){
       return this.roleServiceImp.findyByRolename(rolename);
    }

}
