package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.CategoryDTO;
import com.example.LibraryManagementSystem.mapper.CategoryMapper;
import com.example.LibraryManagementSystem.model.Category;
import com.example.LibraryManagementSystem.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testFindAll_ShouldReturnListOfCategoryDTOs() {
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("Category A");

        Category c2 = new Category();
        c2.setId(2L);
        c2.setName("Category B");

        List<Category> categories = List.of(c1, c2);
        when(categoryRepository.findAll()).thenReturn(categories);

        try (MockedStatic<CategoryMapper> mocked = mockStatic(CategoryMapper.class)) {
            mocked.when(() -> CategoryMapper.toDTO(c1)).thenReturn(new CategoryDTO(1L, "Category A", null));
            mocked.when(() -> CategoryMapper.toDTO(c2)).thenReturn(new CategoryDTO(2L, "Category B", null));

            List<CategoryDTO> result = categoryService.findAll();

            assertEquals(2, result.size());
            assertEquals("Category A", result.get(0).getName());
            assertEquals("Category B", result.get(1).getName());
        }
    }

    @Test
    void testFindById_ShouldReturnCategoryDTO_WhenExists() {
        Category c = new Category();
        c.setId(1L);
        c.setName("History");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(c));

        try (MockedStatic<CategoryMapper> mocked = mockStatic(CategoryMapper.class)) {
            mocked.when(() -> CategoryMapper.toDTO(c)).thenReturn(new CategoryDTO(1L, "History", null));

            CategoryDTO result = categoryService.findById(1L);

            assertNotNull(result);
            assertEquals("History", result.getName());
        }
    }

    @Test
    void testFindById_ShouldThrow_WhenNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> categoryService.findById(99L));

        assertEquals("Kateqoriya tapılmadı!", ex.getMessage());
    }

    @Test
    void testSave_ShouldReturnSavedCategoryDTO() {
        CategoryDTO input = new CategoryDTO(null, "Science", null);
        Category entity = new Category();
        entity.setId(1L);
        entity.setName("Science");

        when(categoryRepository.save(any(Category.class))).thenReturn(entity);

        try (MockedStatic<CategoryMapper> mocked = mockStatic(CategoryMapper.class)) {
            mocked.when(() -> CategoryMapper.toEntity(input)).thenReturn(entity);
            mocked.when(() -> CategoryMapper.toDTO(entity)).thenReturn(new CategoryDTO(1L, "Science", null));

            CategoryDTO result = categoryService.save(input);

            assertNotNull(result);
            assertEquals("Science", result.getName());
        }
    }

    @Test
    void testUpdate_ShouldReturnUpdatedCategoryDTO() {
        Category existing = new Category();
        existing.setId(1L);
        existing.setName("Old Name");

        Category updated = new Category();
        updated.setId(1L);
        updated.setName("New Name");

        CategoryDTO input = new CategoryDTO(1L, "New Name", null);
        CategoryDTO expected = new CategoryDTO(1L, "New Name", null);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(existing)).thenReturn(updated);

        try (MockedStatic<CategoryMapper> mocked = mockStatic(CategoryMapper.class)) {
            mocked.when(() -> CategoryMapper.toDTO(updated)).thenReturn(expected);

            CategoryDTO result = categoryService.update(1L, input);

            assertEquals("New Name", result.getName());
            verify(categoryRepository).save(existing);
        }
    }

    @Test
    void testUpdate_ShouldThrow_WhenNotFound() {
        when(categoryRepository.findById(77L)).thenReturn(Optional.empty());

        CategoryDTO input = new CategoryDTO(77L, "Doesn't Matter", null);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> categoryService.update(77L, input));

        assertEquals("Kateqoriya tapılmadı!", ex.getMessage());
    }

    @Test
    void testDelete_ShouldCallRepository() {
        categoryService.delete(1L);
        verify(categoryRepository).deleteById(1L);
    }
}