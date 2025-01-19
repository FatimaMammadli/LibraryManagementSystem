package com.example.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
    @Table(name = "orders")
    @Getter
    @Setter
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String bookTitle;
        private String studentName;

        @ManyToOne
        private Book book;

        @ManyToOne
        private Student student;

        private String status;
    }

