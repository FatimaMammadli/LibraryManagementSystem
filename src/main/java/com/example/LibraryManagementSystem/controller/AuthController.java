package com.example.LibraryManagementSystem.controller;

import ch.qos.logback.core.model.Model;
import com.example.LibraryManagementSystem.model.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", new UserEntity());
        return modelAndView;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserEntity user) {
        return "redirect:/dashboard";
    }

    @GetMapping("/admin")
    public String adminDashboard() {
        return "dashboard";
    }
}

