package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.OrderDTO;
import com.example.LibraryManagementSystem.mapper.OrderMapper;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.OrderRepository;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private BookRepository bookRepository;

    @InjectMocks private OrderService orderService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testFindAll_ShouldReturnDTOList() {
        Student s = new Student(); s.setId(1L); s.setName("Ali");
        Book b = new Book(); b.setId(2L); b.setName("Book A");

        Order order = new Order(); order.setId(10L);
        order.setStudent(s); order.setBook(b);
        order.setOrderDate(LocalDateTime.now());

        when(orderRepository.findAll()).thenReturn(List.of(order));

        try (MockedStatic<OrderMapper> mocked = mockStatic(OrderMapper.class)) {
            OrderDTO dto = new OrderDTO(10L, 1L, 2L, "Ali", "Book A", order.getOrderDate());
            mocked.when(() -> OrderMapper.toDTO(order)).thenReturn(dto);

            List<OrderDTO> result = orderService.findAll();

            assertEquals(1, result.size());
            assertEquals("Ali", result.get(0).getStudentName());
            assertEquals("Book A", result.get(0).getBookTitle());
        }
    }

    @Test
    void testFindById_ShouldReturnDTO() {
        Student s = new Student(); s.setId(1L); s.setName("Ali");
        Book b = new Book(); b.setId(2L); b.setName("Book B");

        Order order = new Order();
        order.setId(5L); order.setStudent(s); order.setBook(b);
        order.setOrderDate(LocalDateTime.now());

        when(orderRepository.findById(5L)).thenReturn(Optional.of(order));

        try (MockedStatic<OrderMapper> mocked = mockStatic(OrderMapper.class)) {
            OrderDTO dto = new OrderDTO(5L, 1L, 2L, "Ali", "Book B", order.getOrderDate());
            mocked.when(() -> OrderMapper.toDTO(order)).thenReturn(dto);

            OrderDTO result = orderService.findById(5L);

            assertNotNull(result);
            assertEquals("Ali", result.getStudentName());
            assertEquals("Book B", result.getBookTitle());
        }
    }

    @Test
    void testFindById_ShouldThrow_WhenNotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> orderService.findById(999L));

        assertEquals("Sifariş tapılmadı!", ex.getMessage());
    }

    @Test
    void testSave_ShouldWork_WhenBookInStock() {
        OrderDTO input = new OrderDTO(null, 1L, 2L, null, null, null);
        Student s = new Student(); s.setId(1L); s.setName("Ali");
        Book b = new Book(); b.setId(2L); b.setName("Book"); b.setQuantity(3);
        Order order = new Order(); order.setId(100L); order.setStudent(s); order.setBook(b); order.setOrderDate(LocalDateTime.now());

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(b));
        when(bookRepository.save(any(Book.class))).thenReturn(b);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        try (MockedStatic<OrderMapper> mocked = mockStatic(OrderMapper.class)) {
            mocked.when(() -> OrderMapper.toEntity(input, s, b)).thenReturn(order);
            mocked.when(() -> OrderMapper.toDTO(order)).thenReturn(
                    new OrderDTO(100L, 1L, 2L, "Ali", "Book", order.getOrderDate())
            );

            OrderDTO result = orderService.save(input);

            assertNotNull(result);
            assertEquals("Ali", result.getStudentName());
            assertEquals("Book", result.getBookTitle());
            assertEquals(2, b.getQuantity()); // quantity 3 - 1
        }
    }

    @Test
    void testSave_ShouldThrow_WhenBookOutOfStock() {
        Book book = new Book(); book.setId(2L); book.setQuantity(0);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));

        OrderDTO dto = new OrderDTO(null, 1L, 2L, null, null, null);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> orderService.save(dto));

        assertEquals("Kitab anbarda yoxdur!", ex.getMessage());
    }

    @Test
    void testSave_ShouldThrow_WhenStudentNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        OrderDTO dto = new OrderDTO(null, 999L, 2L, null, null, null);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> orderService.save(dto));

        assertEquals("Tələbə tapılmadı!", ex.getMessage());
    }

    @Test
    void testSave_ShouldThrow_WhenBookNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
        when(bookRepository.findById(888L)).thenReturn(Optional.empty());

        OrderDTO dto = new OrderDTO(null, 1L, 888L, null, null, null);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> orderService.save(dto));

        assertEquals("Kitab tapılmadı!", ex.getMessage());
    }

    @Test
    void testReturnOrder_ShouldRestoreBookAndDelete() {
        Book book = new Book(); book.setQuantity(2);
        Order order = new Order(); order.setId(10L); order.setBook(book);

        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));

        orderService.returnOrder(10L);

        assertEquals(3, book.getQuantity());
        verify(bookRepository).save(book);
        verify(orderRepository).deleteById(10L);
    }

    @Test
    void testReturnOrder_ShouldThrow_WhenNotFound() {
        when(orderRepository.findById(555L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> orderService.returnOrder(555L));

        assertEquals("Sifariş tapılmadı!", ex.getMessage());
    }

    @Test
    void testDelete_ShouldWork_WhenExists() {
        when(orderRepository.existsById(5L)).thenReturn(true);

        orderService.delete(5L);

        verify(orderRepository).deleteById(5L);
    }

    @Test
    void testDelete_ShouldThrow_WhenNotFound() {
        when(orderRepository.existsById(999L)).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> orderService.delete(999L));

        assertEquals("Sifariş tapılmadı!", ex.getMessage());
    }
}
