package com.example.LibraryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDTO {
    private Long id;
    private String name;
    private List<Long> bookIds;


    public AuthorDTO(long l, String newAuthor) {
    }
}