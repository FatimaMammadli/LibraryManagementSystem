package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.DTO.OrderDTO;
import com.example.LibraryManagementSystem.mapper.OrderMapper;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.OrderRepository;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;

    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sifariş tapılmadı!"));
        return OrderMapper.toDTO(order);
    }

    @Transactional
    public OrderDTO save(OrderDTO orderDTO) {
        Student student = studentRepository.findById(orderDTO.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Tələbə tapılmadı!"));

        Book book = bookRepository.findById(orderDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı!"));

        if (book.getQuantity() <= 0) {
            throw new IllegalArgumentException("Kitab anbarda yoxdur!");
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        Order order = OrderMapper.toEntity(orderDTO, student, book);
        return OrderMapper.toDTO(orderRepository.save(order));
    }

    @Transactional
    public void returnOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sifariş tapılmadı!"));

        Book book = order.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        orderRepository.deleteById(id);
    }

    @Transactional
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Sifariş tapılmadı!");
        }
        orderRepository.deleteById(id);
    }
}
