package com.example.LibraryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private Long id;
    private String name;
    private String isbn;
    private String image;
    private List<Long> authorIds;
    private String publisher;
    private int publishedYear;
    private int quantity;
    private Long categoryId;
    private List<String> authorNames;

}