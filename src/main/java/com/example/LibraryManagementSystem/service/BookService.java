package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BookDTO;
import com.example.LibraryManagementSystem.mapper.BookMapper;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Category;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public List<BookDTO> findAll() {
        log.info("Find all books");
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO save(BookDTO bookDTO) {
        log.info("Save book");
        Category category = categoryRepository.findById(bookDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Kateqoriya tapılmadı!"));
        List<Author> authors = authorRepository.findAllById(bookDTO.getAuthorIds());

        Book book = BookMapper.toEntity(bookDTO, category, authors);
        book = bookRepository.save(book);

        BookDTO savedBookDTO = BookMapper.toDTO(book);
        savedBookDTO.setAuthorNames(authors.stream().map(Author::getName).collect(Collectors.toList())); // Əlavə edildi

        return savedBookDTO;
    }


    public BookDTO update(Long id, BookDTO bookDTO) {
        log.info("Update book");
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı!"));

        existingBook.setName(bookDTO.getName());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setPublisher(bookDTO.getPublisher());
        existingBook.setPublishedYear(bookDTO.getPublishedYear());
        existingBook.setQuantity(bookDTO.getQuantity());

        return BookMapper.toDTO(bookRepository.save(existingBook));
    }

    public void delete(Long id) {
        log.info("Delete book");
        bookRepository.deleteById(id);
    }

    public Optional<BookDTO> findById(Long id) {
        log.info("Find book by id");
        return bookRepository.findById(id).map(BookMapper::toDTO);
    }

    public List<BookDTO> findByCategory(Long categoryId) {
        log.info("Find books by category");
        List<Book> books = bookRepository.findBookByCategory_Id(categoryId);
        return books.stream().map(BookMapper::toDTO).collect(Collectors.toList());
    }
}
