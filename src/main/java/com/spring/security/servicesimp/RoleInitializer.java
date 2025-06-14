package com.spring.security.servicesimp;

import com.spring.security.entity.Role;
import com.spring.security.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        createRoleIfNotExists("ROLE_ADMI");
        createRoleIfNotExists("ROLE_TECH");
        createRoleIfNotExists("ROLE_USER");
    }

    private void createRoleIfNotExists(String roleName) {
        Role existingRole = roleRepository.findByRolename(roleName);
        if (existingRole == null) {
            Role role = new Role();
            role.setRolename(roleName);
            roleRepository.save(role);
        }
    }

}
