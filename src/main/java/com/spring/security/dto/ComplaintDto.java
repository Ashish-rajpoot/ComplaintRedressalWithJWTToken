package com.spring.security.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComplaintDto {
    private String description;
    private String date;
    private int submittedById;
    private int assignedToId;
    // Add other properties as needed
}

