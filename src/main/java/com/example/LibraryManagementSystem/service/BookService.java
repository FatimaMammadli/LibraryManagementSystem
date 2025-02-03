package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    // ISBN ilə kitab axtar
    public List<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    // Kateqoriyaya görə kitabları tap
    public List<Book> findBooksByCategory(Long categoryId) {
        return bookRepository.findByCategory_Id(categoryId);
    }

    // Anbarda olan kitabları tap
    public List<Book> findAvailableBooks() {
        return bookRepository.findByQuantityGreaterThan(0);
    }

    // Kitabı yenilə
    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setName(updatedBook.getName());
            book.setIsbn(updatedBook.getIsbn());
            book.setImage(updatedBook.getImage());
            book.setPublisher(updatedBook.getPublisher());
            book.setPublishedYear(updatedBook.getPublishedYear());
            book.setQuantity(updatedBook.getQuantity());
            book.setAuthors(updatedBook.getAuthors());
            book.setCategory(updatedBook.getCategory());
            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }
    }
}
