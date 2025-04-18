package com.example.LibraryManagementSystem.controller;
import com.example.LibraryManagementSystem.model.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "dashboard";
    }
}

