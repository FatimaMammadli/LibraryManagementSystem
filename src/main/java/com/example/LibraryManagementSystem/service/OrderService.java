package com.example.LibraryManagementSystem.service;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.OrderRepository;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order createOrder(Order order) {
        Book book = bookRepository.findById(order.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);
            order.setOrderDate(LocalDateTime.now());
            return orderRepository.save(order);
        } else {
            throw new IllegalStateException("Book is out of stock");
        }
    }

    @Transactional
    public void returnOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Book book = order.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
        orderRepository.deleteById(id);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
