package com.example.LibraryManagementSystem.service;

import ch.qos.logback.core.model.Model;
import com.example.LibraryManagementSystem.DTO.AuthorDTO;
import com.example.LibraryManagementSystem.mapper.AuthorMapper;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public List<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + id));
        return AuthorMapper.toDTO(author);
    }

    public AuthorDTO save(AuthorDTO authorDTO) {
//        List<Book> books = bookRepository.findAllById(authorDTO.getBookIds());
        Author author = AuthorMapper.toEntity(authorDTO);
        author = authorRepository.save(author);
        return AuthorMapper.toDTO(author);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorDTO update(Long id, AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + id));

//        List<Book> books = bookRepository.findAllById(authorDTO.getBookIds());
        existingAuthor.setName(authorDTO.getName());
//        existingAuthor.setBooks(books);

        return AuthorMapper.toDTO(authorRepository.save(existingAuthor));
    }
}