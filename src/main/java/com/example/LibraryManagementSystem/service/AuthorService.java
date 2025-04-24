package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.AuthorDTO;
import com.example.LibraryManagementSystem.mapper.AuthorMapper;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorDTO> findAll() {
        log.info("Find all Authors");
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO findById(Long id) {
        log.info("Find Author by ID {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + id));
        return authorMapper.toDTO(author);
    }

    public AuthorDTO save(AuthorDTO authorDTO) {
        log.info("Save Author {}", authorDTO);
        Author author = authorMapper.toEntity(authorDTO);
        author = authorRepository.save(author);
        return authorMapper.toDTO(author);
    }

    public void deleteById(Long id) {
        log.info("Delete Author by ID {}", id);
        authorRepository.deleteById(id);
    }

    public AuthorDTO update(Long id, AuthorDTO authorDTO) {
        log.info("Update Author {}", authorDTO);
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + id));

        existingAuthor.setName(authorDTO.getName());

        return authorMapper.toDTO(authorRepository.save(existingAuthor));
    }
}