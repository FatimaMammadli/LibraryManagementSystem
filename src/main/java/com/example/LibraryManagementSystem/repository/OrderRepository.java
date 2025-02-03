package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStudentId(Long studentId);
}

