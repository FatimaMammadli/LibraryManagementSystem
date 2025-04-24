package com.example.LibraryManagementSystem.service;



import com.example.LibraryManagementSystem.dto.BookDTO;
import com.example.LibraryManagementSystem.mapper.BookMapper;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Category;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    class BookServiceTest {

        @Mock private BookRepository bookRepository;
        @Mock private CategoryRepository categoryRepository;
        @Mock private AuthorRepository authorRepository;

        @InjectMocks private BookService bookService;

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
        void testFindAll_ShouldReturnListOfBookDTOs() {
            List<Book> books = List.of(new Book(1L, "Book 1"), new Book(2L, "Book 2"));

            when(bookRepository.findAll()).thenReturn(books);

            try (MockedStatic<BookMapper> mockedMapper = mockStatic(BookMapper.class)) {
                mockedMapper.when(() -> BookMapper.toDTO(books.get(0)))
                        .thenReturn(new BookDTO(1L, "Book 1", "isbn", null, null, null, 2020, 1, null, null));
                mockedMapper.when(() -> BookMapper.toDTO(books.get(1)))
                        .thenReturn(new BookDTO(2L, "Book 2", "isbn2", null, null, null, 2021, 2, null, null));

                List<BookDTO> result = bookService.findAll();

                assertEquals(2, result.size());
                assertEquals("Book 1", result.get(0).getName());
                assertEquals("Book 2", result.get(1).getName());
            }
        }

        @Test
        void testSave_ShouldSaveBookAndReturnDTO() {
            BookDTO input = new BookDTO(null, "New Book", "isbn", null, List.of(1L, 2L), "Publisher", 2022, 5, 10L, null);
            Category category = new Category();
            category.setId(10L);

            // Doğru şəkildə müəlliflər yaradılır
            Author a1 = new Author();
            a1.setId(1L);
            a1.setName("A1");

            Author a2 = new Author();
            a2.setId(2L);
            a2.setName("A2");

            List<Author> authors = List.of(a1, a2);

            Book book = new Book(1L, "New Book");
            book.setAuthors(authors);
            book.setCategory(category);

            when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
            when(authorRepository.findAllById(List.of(1L, 2L))).thenReturn(authors);
            when(bookRepository.save(any(Book.class))).thenReturn(book);

            try (MockedStatic<BookMapper> mockedMapper = mockStatic(BookMapper.class)) {
                mockedMapper.when(() -> BookMapper.toEntity(input, category, authors)).thenReturn(book);
                mockedMapper.when(() -> BookMapper.toDTO(book))
                        .thenReturn(new BookDTO(
                                1L, "New Book", "isbn", null, List.of(1L, 2L),
                                "Publisher", 2022, 5, 10L, List.of("A1", "A2")
                        ));

                BookDTO result = bookService.save(input);

                assertNotNull(result);
                assertEquals("New Book", result.getName());
                assertEquals(List.of("A1", "A2"), result.getAuthorNames());
            }
        }


        @Test
        void testSave_ShouldThrow_WhenCategoryNotFound() {
            BookDTO input = new BookDTO(null, "Book", "isbn", null, List.of(1L), "Pub", 2020, 3, 10L, null);
            when(categoryRepository.findById(10L)).thenReturn(Optional.empty());

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                    bookService.save(input));

            assertEquals("Kateqoriya tapılmadı!", ex.getMessage());
        }

        @Test
        void testUpdate_ShouldModifyAndReturnUpdatedDTO() {
            Book existingBook = new Book(1L, "Old");
            existingBook.setIsbn("111");
            existingBook.setPublisher("P");
            existingBook.setPublishedYear(2010);
            existingBook.setQuantity(1);

            Book updatedBook = new Book(1L, "Updated Book");
            updatedBook.setIsbn("222");
            updatedBook.setPublisher("NewPub");
            updatedBook.setPublishedYear(2023);
            updatedBook.setQuantity(4);

            BookDTO dto = new BookDTO(1L, "Updated Book", "222", null, null, "NewPub", 2023, 4, null, null);
            BookDTO returnedDTO = new BookDTO(1L, "Updated Book", "222", null, null, "NewPub", 2023, 4, null, null);

            when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
            when(bookRepository.save(existingBook)).thenReturn(updatedBook);

            try (MockedStatic<BookMapper> mocked = mockStatic(BookMapper.class)) {
                mocked.when(() -> BookMapper.toDTO(updatedBook)).thenReturn(returnedDTO);

                BookDTO result = bookService.update(1L, dto);

                assertEquals("Updated Book", result.getName());
                assertEquals("222", result.getIsbn());
            }
        }

        @Test
        void testUpdate_ShouldThrow_WhenBookNotFound() {
            when(bookRepository.findById(5L)).thenReturn(Optional.empty());
            BookDTO dto = new BookDTO(5L, "Any", "isbn", null, null, "P", 2020, 2, null, null);

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                    bookService.update(5L, dto));

            assertEquals("Kitab tapılmadı!", ex.getMessage());
        }

        @Test
        void testDelete_ShouldCallRepository() {
            bookService.delete(1L);
            verify(bookRepository).deleteById(1L);
        }

        @Test
        void testFindById_ShouldReturnBookDTO_WhenExists() {
            Book book = new Book(1L, "Found Book");
            when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

            try (MockedStatic<BookMapper> mocked = mockStatic(BookMapper.class)) {
                BookDTO dto = new BookDTO(1L, "Found Book", null, null, null, null, 2020, 1, null, null);
                mocked.when(() -> BookMapper.toDTO(book)).thenReturn(dto);

                Optional<BookDTO> result = bookService.findById(1L);

                assertTrue(result.isPresent());
                assertEquals("Found Book", result.get().getName());
            }
        }

        @Test
        void testFindById_ShouldReturnEmpty_WhenNotFound() {
            when(bookRepository.findById(99L)).thenReturn(Optional.empty());
            Optional<BookDTO> result = bookService.findById(99L);
            assertFalse(result.isPresent());
        }

        @Test
        void testFindByCategory_ShouldReturnBookDTOs() {
            Category category = new Category();
            category.setId(5L);
            Book book = new Book(1L, "Book C");
            List<Book> books = List.of(book);

            when(bookRepository.findBookByCategory_Id(5L)).thenReturn(books);

            try (MockedStatic<BookMapper> mocked = mockStatic(BookMapper.class)) {
                mocked.when(() -> BookMapper.toDTO(book))
                        .thenReturn(new BookDTO(1L, "Book C", null, null, null, null, 2020, 1, 5L, null));

                List<BookDTO> result = bookService.findByCategory(5L);
                assertEquals(1, result.size());
                assertEquals("Book C", result.get(0).getName());
            }
        }

}

