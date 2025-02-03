package com.example.LibraryManagementSystem.controller;

import ch.qos.logback.core.model.Model;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Category;
import com.example.LibraryManagementSystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/categories")
public class CategoryController {


    private final CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addText("categories");
        return "categories/list";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addText("category");
        return "categories/create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/dashboard/categories";
    }

    @GetMapping("/view/{id}")
    public String viewCategory(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addText("category");
            List<Book> books = categoryService.getBooksByCategory(id);
            model.addText("books");
            return "categories/view";
        }
        return "redirect:/dashboard/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addText("category");
            return "categories/edit";
        }
        return "redirect:/dashboard/categories";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        category.setId(id);
        categoryService.save(category);
        return "redirect:/dashboard/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/dashboard/categories";
    }
}