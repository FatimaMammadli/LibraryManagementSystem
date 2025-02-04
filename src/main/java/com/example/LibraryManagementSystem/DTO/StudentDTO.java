package com.example.LibraryManagementSystem.DTO;

import lombok.Data;
import java.util.List;

@Data
public class StudentDTO {
    private Long id;
    private String sif;
    private String name;
    private String email;
    private List<Long> orderIds;

    public StudentDTO() {}

    public StudentDTO(Long id, String sif, String name, String email, List<Long> orderIds) {
        this.id = id;
        this.sif = sif;
        this.name = name;
        this.email = email;
        this.orderIds = orderIds;
    }
}
