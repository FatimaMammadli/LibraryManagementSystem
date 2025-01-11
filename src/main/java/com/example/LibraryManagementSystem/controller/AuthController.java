package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.model.UserEntity;
import com.example.LibraryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", new UserEntity());
        return modelAndView;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserEntity user) {
        Optional<UserEntity> foundUser = userRepository.findByUsername(user.getUsername());

        return "Invalid credentials";
    }
}
