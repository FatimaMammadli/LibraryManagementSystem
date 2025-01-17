package com.example.LibraryManagementSystem.controller;

import ch.qos.logback.core.model.Model;
import com.example.LibraryManagementSystem.model.Category;
import com.example.LibraryManagementSystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addText("categories");
        return "categories/list"; // Renders categories list (templates/categories/list.html)
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addText("category");
        return "categories/create"; // Renders the create form (templates/categories/create.html)
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories"; // Redirects back to categories list
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        model.addText("category");
        return "categories/edit"; // Renders the edit form (templates/categories/edit.html)
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id);
        categoryService.save(category);
        return "redirect:/categories"; // Redirects back to categories list
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/categories"; // Redirects back to categories list
    }
}