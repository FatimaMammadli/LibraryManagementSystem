package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.CategoryDTO;
import com.example.LibraryManagementSystem.mapper.CategoryMapper;
import com.example.LibraryManagementSystem.model.Category;
import com.example.LibraryManagementSystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        log.info("findAll");
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(Long id) {
        log.info("findById");
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kateqoriya tapılmadı!"));
        return CategoryMapper.toDTO(category);
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.info("save");
//        List<Book> books = bookRepository.findAllById(categoryDTO.getBookIds());
        Category category = CategoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return CategoryMapper.toDTO(category);
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        log.info("update");
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
