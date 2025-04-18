package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.StudentDTO;
import com.example.LibraryManagementSystem.model.Order;
import com.example.LibraryManagementSystem.repository.OrderRepository;
import com.example.LibraryManagementSystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/students")
public class StudentController {

    private final StudentService studentService;
    private final OrderRepository orderRepository;

    @GetMapping
    public String listStudents(Model model) {
        List<StudentDTO> students = studentService.findAll();
        model.addAttribute("students", students);
        return "students/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new StudentDTO());
        model.addAttribute("order", new Order());
        return "students/create";
    }

    @PostMapping("/create")
    public String createStudent(@ModelAttribute StudentDTO studentDTO) {
        studentService.save(studentDTO);
        return "redirect:/dashboard/students";
    }

    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        StudentDTO student = studentService.findById(id);
        model.addAttribute("student", student);
        return "students/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        StudentDTO student = studentService.findById(id);
        model.addAttribute("student", student);
        return "students/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute StudentDTO studentDTO) {
        studentService.update(id, studentDTO);
        return "redirect:/dashboard/students";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/dashboard/students";
    }
    @GetMapping("/order/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sifariş tapılmadı!"));

        model.addAttribute("order", order);
        return "order/details";
    }

}
