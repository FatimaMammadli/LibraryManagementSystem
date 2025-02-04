package com.example.LibraryManagementSystem.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private Long studentId;
    private Long bookId;
    private String studentName;
    private String bookTitle;
    private LocalDateTime orderDate;

    public OrderDTO() {}

    public OrderDTO(Long id, Long studentId, Long bookId, String studentName, String bookTitle, LocalDateTime orderDate) {
        this.id = id;
        this.studentId = studentId;
        this.bookId = bookId;
        this.studentName = studentName;
        this.bookTitle = bookTitle;
        this.orderDate = orderDate;
    }
}
