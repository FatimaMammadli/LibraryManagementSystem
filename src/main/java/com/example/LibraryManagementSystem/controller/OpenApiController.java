package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.AuthorDTO;
import com.example.LibraryManagementSystem.dto.BookDTO;
import com.example.LibraryManagementSystem.dto.CategoryDTO;
import com.example.LibraryManagementSystem.service.AuthorService;
import com.example.LibraryManagementSystem.service.BookService;
import com.example.LibraryManagementSystem.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Library API", description = "Public API for accessing books, authors, and categories")
public class OpenApiController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    @Operation(summary = "Get books by category", description = "Retrieve books filtered by category ID")
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getBooksByCategory(@RequestParam(value = "category", required = false) Long categoryId) {
        List<BookDTO> books = (categoryId == null) ? bookService.findAll() : bookService.findByCategory(categoryId);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get all categories", description = "Retrieve a list of all book categories")
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get all authors", description = "Retrieve a list of all authors")
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @Operation(summary = "Get book details", description = "Retrieve detailed information about a specific book")
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı!"));
        return ResponseEntity.ok(book);
    }
}
