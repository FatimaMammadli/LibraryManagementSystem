package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.CategoryDTO;
import com.example.LibraryManagementSystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @GetMapping("/view/{id}")
    public String viewCategory(@PathVariable Long id, Model model) {
        CategoryDTO categoryDTO = categoryService.findById(id);
        model.addAttribute("category", categoryDTO);
        return "categories/view";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "categories/create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return "redirect:/dashboard/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CategoryDTO categoryDTO = categoryService.findById(id);
        model.addAttribute("category", categoryDTO);
        return "categories/edit";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute CategoryDTO categoryDTO) {
        categoryService.update(id, categoryDTO);
        return "redirect:/dashboard/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/dashboard/categories";
    }
}
