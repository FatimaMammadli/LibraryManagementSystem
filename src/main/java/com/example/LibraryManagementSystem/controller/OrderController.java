package com.example.LibraryManagementSystem.controller;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/list";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        orderService.save(order);
        return "redirect:/orders";
    }
}
