package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.dto.AuthorDTO;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {

        public  AuthorDTO toDTO(Author author) {
            if (author == null) {
                return null;
            }
            List<Long> bookIds = author.getBooks() != null ?
                    author.getBooks().stream().map(Book::getId).collect(Collectors.toList()) : null;

            return new AuthorDTO(author.getId(), author.getName(), bookIds);
        }

        public  Author toEntity(AuthorDTO authorDTO) {
            if (authorDTO == null) {
                return null;
            }
            Author author = new Author();
            author.setId(authorDTO.getId());
            author.setName(authorDTO.getName());
            return author;
        }
    }
