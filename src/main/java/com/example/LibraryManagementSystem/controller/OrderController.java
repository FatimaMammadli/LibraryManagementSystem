package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.service.BookService;
import com.example.LibraryManagementSystem.service.OrderService;
import com.example.LibraryManagementSystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/orders")
public class OrderController {

    private final OrderService orderService;
    private final BookService bookService;
    private final StudentService studentService;

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("books", bookService.findAvailableBooks());
        return "orders/create";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        order.setOrderDate(LocalDateTime.now());
        orderService.createOrder(order);
        return "redirect:/dashboard/orders";
    }


    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.findById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "orders/view";
        }
        return "redirect:/dashboard/orders";
    }

    @GetMapping("/return/{id}")
    public String showReturnForm(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.findById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "orders/return";
        }
        return "redirect:/dashboard/orders";
    }

    @PostMapping("/return/{id}")
    public String returnOrder(@PathVariable Long id) {
        orderService.returnOrder(id);
        return "redirect:/dashboard/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/dashboard/orders";
    }
}
