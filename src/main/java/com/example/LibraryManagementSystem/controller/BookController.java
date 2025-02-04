package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.DTO.BookDTO;
import com.example.LibraryManagementSystem.DTO.AuthorDTO;
import com.example.LibraryManagementSystem.service.BookService;
import com.example.LibraryManagementSystem.service.AuthorService;
import com.example.LibraryManagementSystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;


    @GetMapping
    public String listBooks(Model model) {
        List<BookDTO> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/list";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new BookDTO());
        model.addAttribute("authors", authorService.findAll()); // Mövcud müəlliflər
        model.addAttribute("categories", categoryService.findAll()); // Mövcud kateqoriyalar
        return "books/create";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute BookDTO bookDTO) {
        bookService.save(bookDTO);
        return "redirect:/dashboard/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BookDTO book = bookService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı!"));
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll()); // Mövcud müəlliflər
        model.addAttribute("categories", categoryService.findAll()); // Mövcud kateqoriyalar
        return "books/edit";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute BookDTO bookDTO) {
        bookService.update(id, bookDTO);
        return "redirect:/dashboard/books";
    }


    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/dashboard/books";
    }

    @GetMapping("/view/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        BookDTO book = bookService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı! ID: " + id));
        model.addAttribute("book", book);
        return "books/view";
    }
}
