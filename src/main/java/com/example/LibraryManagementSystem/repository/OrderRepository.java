package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

