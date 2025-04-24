package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.StudentDTO;
import com.example.LibraryManagementSystem.mapper.StudentMapper;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.repository.OrderRepository;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    @Mock private OrderRepository orderRepository;
    @InjectMocks private StudentService studentService;

    private AutoCloseable closeable;

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testFindAll_ShouldReturnListOfStudentDTOs() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Ali");
        student.setSif("sif");
        student.setEmail("ali@example.com");

        when(studentRepository.findAll()).thenReturn(List.of(student));

        try (MockedStatic<StudentMapper> mocked = mockStatic(StudentMapper.class)) {
            StudentDTO dto = new StudentDTO(1L, "sif", "Ali", "ali@example.com", null);
            mocked.when(() -> StudentMapper.toDTO(student)).thenReturn(dto);

            List<StudentDTO> result = studentService.findAll();

            assertEquals(1, result.size());
            assertEquals("Ali", result.get(0).getName());
        }
    }

    @Test
    void testFindById_ShouldReturnDTO_WhenExists() {
        Student student = new Student();
        student.setId(2L);
        student.setName("Veli");
        student.setSif("pass");
        student.setEmail("veli@example.com");

        when(studentRepository.findById(2L)).thenReturn(Optional.of(student));

        try (MockedStatic<StudentMapper> mocked = mockStatic(StudentMapper.class)) {
            StudentDTO dto = new StudentDTO(2L, "pass", "Veli", "veli@example.com", null);
            mocked.when(() -> StudentMapper.toDTO(student)).thenReturn(dto);

            StudentDTO result = studentService.findById(2L);

            assertNotNull(result);
            assertEquals("Veli", result.getName());
        }
    }

    @Test
    void testFindById_ShouldThrow_WhenNotFound() {
        when(studentRepository.findById(100L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> studentService.findById(100L));

        assertEquals("Tələbə tapılmadı!", ex.getMessage());
    }

    @Test
    void testSave_ShouldAssignDefaultPassword_IfEmpty() {
        StudentDTO input = new StudentDTO(null, "", "Orxan", "orxan@example.com", null);
        Student student = new Student();
        student.setId(10L);
        student.setName("Orxan");
        student.setSif(""); // will be default
        student.setEmail("orxan@example.com");

        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> {
            Student s = invocation.getArgument(0);
            s.setId(10L);
            return s;
        });

        try (MockedStatic<StudentMapper> mocked = mockStatic(StudentMapper.class)) {
            mocked.when(() -> StudentMapper.toEntity(input)).thenReturn(student);
            mocked.when(() -> StudentMapper.toDTO(any(Student.class)))
                    .thenAnswer(invocation -> {
                        Student s = invocation.getArgument(0);
                        return new StudentDTO(s.getId(), s.getSif(), s.getName(), s.getEmail(), null);
                    });

            StudentDTO result = studentService.save(input);

            assertNotNull(result);
            assertEquals("defaultpassword", result.getSif());
            assertEquals("Orxan", result.getName());
        }
    }

    @Test
    void testSave_ShouldSave_WhenSifPresent() {
        StudentDTO input = new StudentDTO(null, "abc123", "Leyla", "leyla@example.com", null);
        Student student = new Student();
        student.setSif("abc123");
        student.setName("Leyla");
        student.setEmail("leyla@example.com");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        try (MockedStatic<StudentMapper> mocked = mockStatic(StudentMapper.class)) {
            mocked.when(() -> StudentMapper.toEntity(input)).thenReturn(student);
            mocked.when(() -> StudentMapper.toDTO(student))
                    .thenReturn(new StudentDTO(5L, "abc123", "Leyla", "leyla@example.com", null));

            StudentDTO result = studentService.save(input);

            assertEquals("Leyla", result.getName());
            assertEquals("abc123", result.getSif());
        }
    }

    @Test
    void testUpdate_ShouldModifyAndReturnStudentDTO() {
        StudentDTO dto = new StudentDTO(null, "newpass", "Updated", "new@example.com", null);
        Student existing = new Student();
        existing.setId(3L);
        existing.setName("Old");
        existing.setEmail("old@example.com");
        existing.setSif("oldpass");

        when(studentRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(studentRepository.save(any(Student.class))).thenReturn(existing);

        try (MockedStatic<StudentMapper> mocked = mockStatic(StudentMapper.class)) {
            mocked.when(() -> StudentMapper.toDTO(existing))
                    .thenReturn(new StudentDTO(3L, "newpass", "Updated", "new@example.com", null));

            StudentDTO result = studentService.update(3L, dto);

            assertEquals("Updated", result.getName());
            assertEquals("newpass", result.getSif());
        }
    }

    @Test
    void testUpdate_ShouldThrow_WhenStudentNotFound() {
        when(studentRepository.findById(77L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> studentService.update(77L, new StudentDTO()));

        assertEquals("Tələbə tapılmadı!", ex.getMessage());
    }

    @Test
    void testDelete_ShouldWork_WhenExists() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.delete(1L);

        verify(studentRepository).deleteById(1L);
    }

    @Test
    void testDelete_ShouldThrow_WhenNotFound() {
        when(studentRepository.existsById(2L)).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> studentService.delete(2L));

        assertEquals("Tələbə tapılmadı!", ex.getMessage());
    }

    @Test
    void testGetOrdersByStudentId_ShouldReturnOrderList() {
        Order o1 = new Order(); o1.setId(101L);
        Order o2 = new Order(); o2.setId(102L);

        when(orderRepository.findAllByStudentId(9L)).thenReturn(List.of(o1, o2));

        List<Order> result = studentService.getOrdersByStudentId(9L);

        assertEquals(2, result.size());
        assertEquals(101L, result.get(0).getId());
    }
}
