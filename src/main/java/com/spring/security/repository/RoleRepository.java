package com.spring.security.repository;

import com.spring.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByRolename(Role rolename);
    Role findByRolename(String rolename);

}
