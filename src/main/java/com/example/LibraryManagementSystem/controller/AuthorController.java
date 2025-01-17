package com.example.LibraryManagementSystem.controller;

import ch.qos.logback.core.model.Model;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public String listAuthors(Model model) {
        model.addText("authors");
        return "authors/list"; // Renders authors list (templates/authors/list.html)
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addText("author");
        return "authors/create"; // Renders the create form (templates/authors/create.html)
    }

    @PostMapping("/create")
    public String createAuthor(@ModelAttribute Author author) {
        authorService.save(author);
        return "redirect:/authors"; // Redirects back to authors list
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorService.findAll().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid author ID: " + id));
        model.addText("author");
        return "authors/edit"; // Renders the edit form (templates/authors/edit.html)
    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        author.setId(id);
        authorService.save(author);
        return "redirect:/authors"; // Redirects back to authors list
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteById(id);
        return "redirect:/authors"; // Redirects back to authors list
    }
}