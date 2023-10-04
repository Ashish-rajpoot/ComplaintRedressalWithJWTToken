package com.spring.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "actions")
@ToString
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDateTime timestamp;
    private String editBy;


    @ManyToOne
    @JoinColumn(name = "complaint_id")
    @JsonIgnoreProperties("actions") // This prevents infinite loop during JSON serialization
    private Complaint complaint;



    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
        if (!complaint.getActions().contains(this)) {
            complaint.getActions().add(this);
        }
    }




    // Other properties, constructors, getters, setters, etc.
}
