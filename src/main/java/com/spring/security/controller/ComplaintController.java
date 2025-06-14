package com.spring.security.controller;

import com.spring.security.dto.ComplaintDto;
import com.spring.security.entity.Action;
import com.spring.security.entity.Complaint;
import com.spring.security.entity.Role;
import com.spring.security.entity.User;
import com.spring.security.servicesimp.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/complaint/")
public class
ComplaintController {

    private final RoleServiceImp roleServiceImp;
    private final UserServiceImp userServiceImp;
    private final ComplaintServiceImp complaintServiceImp;
    private final DateServiceImp dateServiceImp;
    private final ActionServiceImp actionServiceImp;
    public ComplaintController(RoleServiceImp roleServiceImp, UserServiceImp userServiceImp, ComplaintServiceImp complaintServiceImp, DateServiceImp dateServiceImp, ActionServiceImp actionServiceImp) {
        this.roleServiceImp = roleServiceImp;
        this.userServiceImp = userServiceImp;
        this.complaintServiceImp = complaintServiceImp;
        this.dateServiceImp = dateServiceImp;
        this.actionServiceImp = actionServiceImp;
    }

    @GetMapping("/getComplaints")
    public ResponseEntity<List<Complaint>> getComplaints(){
        List<Complaint> complaints = this.complaintServiceImp.getComplaints();
        return ResponseEntity.status(HttpStatus.OK).body(complaints);
    }

    @PostMapping("/add")
    @PreAuthorize("permitAll")
    public ResponseEntity<?> addCompalint(@RequestBody ComplaintDto complaintDto) throws ParseException {

        System.out.println(complaintDto);
        User user = this.userServiceImp.findUserById(complaintDto.getSubmittedById());
        
        Role role = this.roleServiceImp.findById((int) complaintDto.getAssignedToId());

        Complaint complaint = new Complaint();
        Action action = new Action();

        complaint.setAssignedTo(role);
        complaint.setSubmittedBy(user);
        complaint.setIsResolved(false);
        complaint.getActions().add(action);

        action.setDescription(complaintDto.getDescription());
        action.setTimestamp(LocalDateTime.now());
        action.setComplaint(complaint);
        action.setEditBy(user.getUsername());
        complaintServiceImp.addComplaint(complaint);

        return ResponseEntity.status(HttpStatus.CREATED).body("Comaplaint Created");
    }


    @GetMapping("/{role}")
    public ResponseEntity<List<Complaint>> getComplaintsByRoles(@PathVariable String role){
        try {
            List<Complaint> complaints = complaintServiceImp.findComplaintByRolename(role);
            System.out.println(complaints);
            return ResponseEntity.ok().body(complaints);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<List<Complaint>> getComaplaintsByEmail(@PathVariable String email){
        try {
            List<Complaint> complaints = this.complaintServiceImp.findComplaintByEmail(email);
            return ResponseEntity.ok().body(complaints);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/forwardComplaint/{complaintid}")
    public ResponseEntity<Complaint> updateRole(@PathVariable int complaintid, @RequestBody int id){
        System.out.println(this.dateServiceImp.getCurrentDate());
        System.out.println(complaintid +" "+ id);
        Complaint complaint = complaintServiceImp.findById(complaintid);
        if(complaint!=null){
            Role role = roleServiceImp.findById(id);
            if(role!=null){
                complaint.setAssignedTo(role);
//                complaint.setDate(this.dateServiceImp.getCurrentDate());
                complaintServiceImp.addComplaint(complaint);
                return ResponseEntity.status(HttpStatus.OK).body(complaint);
            }else {
                throw new RuntimeException("Role Not Found");
            }
        } else {
            throw new RuntimeException("Complaint Not Found");
        }
    }

    @GetMapping("/complaintid/{id}")
    private Complaint getComapliant(@PathVariable int id){
        return complaintServiceImp.findById(id);
    }

    @PutMapping("/updateDescription/{complaintid}")
    public ResponseEntity<?> updateDescription(@PathVariable int complaintid, @RequestBody String description, Principal principal){
        User user = userServiceImp.getUserByEmail(principal.getName());
        System.out.println(this.dateServiceImp.getCurrentDate());
        Complaint complaint = getComapliant(complaintid);
        if(complaint!=null){
            Action action = new Action();
            action.setTimestamp(LocalDateTime.now());
            action.setDescription(description);
            action.setEditBy(user.getUsername());
            action.setComplaint(complaint);

           actionServiceImp.addAction(action);
                return ResponseEntity.status(HttpStatus.OK).body(complaint);
        } else {
            throw new RuntimeException("Complaint Not Found");
        }
    }

    @PutMapping("/isResolved/{complaintId}")
    public Complaint isResolved(@PathVariable int complaintId,@RequestBody Boolean isResolved){
        Complaint complaint = this.complaintServiceImp.findById(complaintId);
        complaint.setIsResolved(isResolved);
        complaintServiceImp.addComplaint(complaint);
        Complaint complaint1 = complaintServiceImp.findById(complaintId);
        return complaint1;
    }

//    @GetMapping("/complaintById/{complaintid}")
//    public Complaint complaintById(@PathVariable int complaintid){
//        return complaintServiceImp.findById(complaintid);
//    }
}
