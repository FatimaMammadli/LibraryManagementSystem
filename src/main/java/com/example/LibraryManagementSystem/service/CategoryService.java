package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.DTO.CategoryDTO;
import com.example.LibraryManagementSystem.mapper.CategoryMapper;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Category;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kateqoriya tapılmadı!"));
        return CategoryMapper.toDTO(category);
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
//        List<Book> books = bookRepository.findAllById(categoryDTO.getBookIds());
        Category category = CategoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return CategoryMapper.toDTO(category);
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kateqoriya tapılmadı!"));

//        List<Book> books = bookRepository.findAllById(categoryDTO.getBookIds());
        existingCategory.setName(categoryDTO.getName());

        return CategoryMapper.toDTO(categoryRepository.save(existingCategory));
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
