package com.example.LibraryManagementSystem.DTO;

import lombok.Data;
import java.util.List;

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

    public BookDTO() {}

    public BookDTO(Long id, String name, String isbn, String image, List<Long> authorIds, String publisher, int publishedYear, int quantity, Long categoryId) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.image = image;
        this.authorIds = authorIds;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }
}
