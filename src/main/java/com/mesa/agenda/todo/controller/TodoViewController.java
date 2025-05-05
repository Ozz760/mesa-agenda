package com.mesa.agenda.todo.controller;

import com.mesa.agenda.todo.domain.Student;
import com.mesa.agenda.todo.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoViewController {

    private final StudentRepository studentRepository;

    public TodoViewController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/todoview")
    public String showTodoView(Model model) {
        // Get the logged-in user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Fetch the student from the database
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Add student information to the model
        model.addAttribute("student", student);

        return "todoview"; // Render the todoview.html template
    }
}
