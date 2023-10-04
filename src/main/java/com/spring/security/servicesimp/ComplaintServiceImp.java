package com.spring.security.servicesimp;

import com.spring.security.entity.Complaint;
import com.spring.security.repository.ComplaintRepository;
import com.spring.security.services.ComplaintService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImp implements ComplaintService {
    private final ComplaintRepository complaintRepository;

    public ComplaintServiceImp(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public void addComplaint(Complaint complaint) {
        complaintRepository.save(complaint);
    }

    public List<Complaint> getComplaints(){
        return complaintRepository.findAll();
    }


    public List<Complaint> findComplaintByRolename(String rolename) {
        return complaintRepository.findByAssignedTo_Rolename(rolename);
    }

    public List<Complaint> findComplaintByEmail(String email){
        return complaintRepository.findBySubmittedBy_Email(email);
    }

    public Complaint findById(int id){
        return complaintRepository.findById( (long) id).orElseThrow(()-> new RuntimeException("Complaint not found"));
    }
}
