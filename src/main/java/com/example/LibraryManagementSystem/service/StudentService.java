package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.DTO.StudentDTO;
import com.example.LibraryManagementSystem.mapper.StudentMapper;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.repository.OrderRepository;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final OrderRepository orderRepository;

    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tələbə tapılmadı!"));
        return StudentMapper.toDTO(student);
    }

    public StudentDTO save(StudentDTO studentDTO) {
        Student student = StudentMapper.toEntity(studentDTO);
        return StudentMapper.toDTO(studentRepository.save(student));
    }

    public StudentDTO update(Long id, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tələbə tapılmadı!"));

        existingStudent.setName(studentDTO.getName());
        existingStudent.setEmail(studentDTO.getEmail());
        existingStudent.setSif(studentDTO.getSif());

        return StudentMapper.toDTO(studentRepository.save(existingStudent));
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Tələbə tapılmadı!");
        }
        studentRepository.deleteById(id);
    }

    public List<Order> getOrdersByStudentId(Long studentId) {
        return orderRepository.findAllByStudentId(studentId);
    }
}
