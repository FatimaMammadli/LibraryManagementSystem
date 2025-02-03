package com.example.LibraryManagementSystem.repository;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.model.Student;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
