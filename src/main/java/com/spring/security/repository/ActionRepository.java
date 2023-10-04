package com.spring.security.repository;

import com.spring.security.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action,Long> {
    List<Action> findByComplaintId(int id);
}
