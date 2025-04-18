package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.AuthorDTO;
import com.example.LibraryManagementSystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public String listAuthors(Model model) {
        List<AuthorDTO> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/view/{id}")
    public String viewAuthor(@PathVariable Long id, Model model) {
        AuthorDTO author = authorService.findById(id);
        model.addAttribute("author", author);
        return "authors/view";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new AuthorDTO());
        return "authors/create";
    }

    @PostMapping("/create")
    public String createAuthor(@ModelAttribute AuthorDTO authorDTO) {
        authorService.save(authorDTO);
        return "redirect:/dashboard/authors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AuthorDTO authorDTO = authorService.findById(id);
        model.addAttribute("author", authorDTO);
        return "authors/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteById(id);
        return "redirect:/dashboard/authors";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute AuthorDTO authorDTO) {
        authorService.update(id, authorDTO);
        return "redirect:/dashboard/authors";
    }

}
