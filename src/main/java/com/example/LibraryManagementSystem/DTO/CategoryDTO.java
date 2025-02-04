package com.example.LibraryManagementSystem.DTO;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private List<Long> bookIds;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, List<Long> bookIds) {
        this.id = id;
        this.name = name;
        this.bookIds = bookIds;
    }
}
