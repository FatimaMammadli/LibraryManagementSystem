package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.AuthorDTO;
import com.example.LibraryManagementSystem.mapper.AuthorMapper;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

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
    void testFindAll_ShouldReturnListOfAuthorDTOs() {
        List<Author> authors = List.of(new Author(1L, "Author One"), new Author(2L, "Author Two"));
        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.toDTO(authors.get(0))).thenReturn(new AuthorDTO(1L, "Author One", null));
        when(authorMapper.toDTO(authors.get(1))).thenReturn(new AuthorDTO(2L, "Author Two", null));

        List<AuthorDTO> result = authorService.findAll();

        assertEquals(2, result.size());
        assertEquals("Author One", result.get(0).getName());
        assertEquals("Author Two", result.get(1).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testFindById_ShouldReturnAuthorDTO_WhenExists() {
        Author author = new Author(1L, "Author One");
        AuthorDTO authorDTO = new AuthorDTO(1L, "Author One", null);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toDTO(author)).thenReturn(authorDTO);

        AuthorDTO result = authorService.findById(1L);

        assertNotNull(result);
        assertEquals("Author One", result.getName());
        verify(authorRepository).findById(1L);
    }

    @Test
    void testFindById_ShouldThrowException_WhenNotFound() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                authorService.findById(99L));

        assertEquals("Author not found with ID: 99", exception.getMessage());
    }

    @Test
    void testSave_ShouldReturnSavedAuthorDTO() {
        AuthorDTO dto = new AuthorDTO(null, "New Author", null);
        Author author = new Author(1L, "New Author");
        AuthorDTO savedDTO = new AuthorDTO(1L, "New Author", null);

        when(authorMapper.toEntity(dto)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toDTO(author)).thenReturn(savedDTO);

        AuthorDTO result = authorService.save(dto);

        assertNotNull(result);
        assertEquals("New Author", result.getName());
        verify(authorMapper).toEntity(dto);
        verify(authorRepository).save(author);
        verify(authorMapper).toDTO(author);
    }

    @Test
    void testDeleteById_ShouldCallRepository() {
        authorService.deleteById(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdate_ShouldModifyAndReturnUpdatedAuthorDTO() {
        Author existingAuthor = new Author(1L, "Old Name");
        Author updatedAuthor = new Author(1L, "Updated Name");
        AuthorDTO updateDTO = new AuthorDTO(1L, "Updated Name", null);
        AuthorDTO expectedDTO = new AuthorDTO(1L, "Updated Name", null);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(existingAuthor)).thenReturn(updatedAuthor);
        when(authorMapper.toDTO(updatedAuthor)).thenReturn(expectedDTO);

        AuthorDTO result = authorService.update(1L, updateDTO);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());

        verify(authorRepository).findById(1L);
        verify(authorRepository).save(existingAuthor);
        verify(authorMapper).toDTO(updatedAuthor);
    }

    @Test
    void testUpdate_ShouldThrowException_WhenAuthorNotFound() {
        when(authorRepository.findById(123L)).thenReturn(Optional.empty());
        AuthorDTO dto = new AuthorDTO(123L, "Doesn't Matter", null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                authorService.update(123L, dto));

        assertEquals("Author not found with ID: 123", exception.getMessage());
    }
}
