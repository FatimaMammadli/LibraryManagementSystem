package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.DTO.BookDTO;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Category;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    // Entity -> DTO
    public static BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }
        List<Long> authorIds = book.getAuthors() != null ?
                book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()) : null;

        return new BookDTO(
                book.getId(),
                book.getName(),
                book.getIsbn(),
                book.getImage(),
                authorIds,
                book.getPublisher(),
                book.getPublishedYear(),
                book.getQuantity(),
                book.getCategory() != null ? book.getCategory().getId() : null
        );
    }

    // DTO -> Entity
    public static Book toEntity(BookDTO bookDTO, Category category, List<Author> authors) {
        if (bookDTO == null) {
            return null;
        }
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setImage(bookDTO.getImage());
        book.setAuthors(authors);
        book.setPublisher(bookDTO.getPublisher());
        book.setPublishedYear(bookDTO.getPublishedYear());
        book.setQuantity(bookDTO.getQuantity());
        book.setCategory(category);
        return book;
    }
}
