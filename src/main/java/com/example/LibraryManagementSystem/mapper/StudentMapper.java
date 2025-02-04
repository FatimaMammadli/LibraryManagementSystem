package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.DTO.StudentDTO;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.model.Student;
import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    // Entity -> DTO
    public static StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }
        List<Long> orderIds = student.getOrders() != null ?
                student.getOrders().stream().map(Order::getId).collect(Collectors.toList()) : null;

        return new StudentDTO(student.getId(), student.getSif(), student.getName(), student.getEmail(), orderIds);
    }

    // DTO -> Entity
    public static Student toEntity(StudentDTO studentDTO) {
        if (studentDTO == null) {
            return null;
        }
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setSif(studentDTO.getSif());
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        return student;
    }
}
