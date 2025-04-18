package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.StudentDTO;
import com.example.LibraryManagementSystem.mapper.StudentMapper;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.repository.OrderRepository;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final OrderRepository orderRepository;

    public List<StudentDTO> findAll() {
        log.info("Find all students");
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO findById(Long id) {
        log.info("Find student by id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tələbə tapılmadı!"));
        return StudentMapper.toDTO(student);
    }

    public StudentDTO save(StudentDTO studentDTO) {
        log.info("Save student: {}", studentDTO);
        Student student = StudentMapper.toEntity(studentDTO);
        if (student.getSif() == null || student.getSif().isEmpty()) {
            student.setSif("defaultpassword");
        }
        return StudentMapper.toDTO(studentRepository.save(student));
    }

    public StudentDTO update(Long id, StudentDTO studentDTO) {
        log.info("Update student: {}", studentDTO);
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tələbə tapılmadı!"));

        existingStudent.setName(studentDTO.getName());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setSif(studentDTO.getSif());

        return StudentMapper.toDTO(studentRepository.save(existingStudent));
    }

    public void delete(Long id) {
        log.info("Delete student: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Tələbə tapılmadı!");
        }
        studentRepository.deleteById(id);
    }

    public List<Order> getOrdersByStudentId(Long studentId) {
        log.info("Get orders by studentId: {}", studentId);
        return orderRepository.findAllByStudentId(studentId);
    }
}
