package com.example.LibraryManagementSystem.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "auth/login"; // Renders the login page (templates/auth/login.html)
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Renders the dashboard page (templates/dashboard.html)
    }
}