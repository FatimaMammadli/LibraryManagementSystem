package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.OrderDTO;
import com.example.LibraryManagementSystem.service.BookService;
import com.example.LibraryManagementSystem.service.OrderService;
import com.example.LibraryManagementSystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/orders")
public class OrderController {

    private final OrderService orderService;
    private final StudentService studentService;
    private final BookService bookService;

    @GetMapping
    public String listOrders(Model model) {
        List<OrderDTO> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new OrderDTO());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("books", bookService.findAll());
        return "orders/create";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderDTO orderDTO) {
        orderService.save(orderDTO);
        return "redirect:/dashboard/orders";
    }

    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        OrderDTO order = orderService.findById(id);
        model.addAttribute("order", order);
        return "orders/view";
    }

    @PostMapping("/return/{id}")
    public String returnOrder(@PathVariable Long id) {
        orderService.returnOrder(id);
        return "redirect:/dashboard/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/dashboard/orders";
    }
}
