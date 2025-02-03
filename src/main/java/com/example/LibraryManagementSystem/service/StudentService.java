package com.example.LibraryManagementSystem.service;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.repository.OrderRepository;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final OrderRepository orderRepository;


    public List<Student> findAll() {
        return studentRepository.findAll();
    }


    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));
    }


    public Student updateStudent(Long id, Student updatedStudent) {
        Student student = findById(id);
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setSif(updatedStudent.getSif());
        return studentRepository.save(student);
    }


    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }


    public List<Order> getOrdersByStudentId(Long studentId) {
        return orderRepository.findAllByStudentId(studentId);
    }
}
