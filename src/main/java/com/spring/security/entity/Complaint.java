package com.spring.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean isResolved;
//    private String description;

    @ManyToOne
    @JoinColumn(name = "submittedBy")
    @JsonIgnoreProperties("users")
    private User submittedBy;

    @ManyToOne
    @JoinColumn(name = "assignedTo")
    @JsonIgnoreProperties("roles")
    private Role assignedTo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "complaint_id")
    private List<Action> actions = new ArrayList<>();
    // Other properties and relationships
}
