package com.example.LibraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String isbn;
    private String image;
    @ManyToMany
    @JoinTable
    private List<Author> authors=new ArrayList<>();
    private String publisher;
    private int publishedYear;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Book(long l, String newBook) {
    }

    public Book() {

    }
}
