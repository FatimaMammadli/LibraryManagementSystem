package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.DTO.CategoryDTO;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Category;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        List<Long> bookIds = category.getBooks() != null ?
                category.getBooks().stream().map(Book::getId).collect(Collectors.toList()) : null;

        return new CategoryDTO(category.getId(), category.getName(), bookIds);
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}
