package com.spring.security.repository;

import com.spring.security.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint , Long> {

    List<Complaint> findByAssignedTo_Rolename(String role);
    List<Complaint> findBySubmittedBy_Email(String email);

}
