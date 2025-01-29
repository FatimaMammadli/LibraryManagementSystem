package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/authors") // Now all routes are under /dashboard/authors
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors/list"; // templates/authors/list.html
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/create"; // templates/authors/create.html
    }

    @PostMapping("/create")
    public String createAuthor(@ModelAttribute Author author) {
        authorService.save(author);
        return "redirect:/dashboard/authors"; // Redirect back to dashboard authors list
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        return "authors/edit"; // templates/authors/edit.html
    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        author.setId(id);
        authorService.save(author);
        return "redirect:/dashboard/authors"; // Ensure consistent redirect to dashboard authors list
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteById(id);
        return "redirect:/dashboard/authors"; // Redirect back to dashboard authors list
    }
}
