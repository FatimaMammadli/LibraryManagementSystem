package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.BookDTO;
import com.example.LibraryManagementSystem.service.BookService;
import com.example.LibraryManagementSystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/website")
public class WebsiteController {

    private final BookService bookService;
    private final CategoryService categoryService;


    @GetMapping
    public String listBooks(@RequestParam(value = "category", required = false) Long categoryId, Model model) {
        List<BookDTO> books = (categoryId == null) ? bookService.findAll() : bookService.findByCategory(categoryId);
        model.addAttribute("books", books);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategory", categoryId);
        return "website/list";
    }


    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        log.info("viewBook id {}", id);
        BookDTO book = bookService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı!"));
        model.addAttribute("book", book);
        return "website/view";
    }
}
