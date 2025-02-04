package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.DTO.OrderDTO;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.model.Student;

import java.time.LocalDateTime;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderDTO(
                order.getId(),
                order.getStudent().getId(),
                order.getBook().getId(),
                order.getStudent().getName(),
                order.getBook().getName(),
                order.getOrderDate()
        );
    }

    public static Order toEntity(OrderDTO orderDTO, Student student, Book book) {
        if (orderDTO == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setStudent(student);
        order.setBook(book);
        order.setOrderDate(orderDTO.getOrderDate() != null ? orderDTO.getOrderDate() : LocalDateTime.now());
        return order;
    }
}
