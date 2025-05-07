package com.mesa.agenda.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mesa.agenda.todo.service.StudentService;

@Controller
public class TodoViewController {

    private final StudentService studentService;

    public TodoViewController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/todoview")
    public String showTodoView(Model model) {
        // Example logic using the service
        model.addAttribute("students", studentService.getAllStudents());
        return "todoview";
    }
}
