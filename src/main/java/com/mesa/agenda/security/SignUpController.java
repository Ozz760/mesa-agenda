package com.mesa.agenda.security;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mesa.agenda.todo.domain.Student;
import com.mesa.agenda.todo.service.StudentService;

@Controller
public class SignUpController {

    @Autowired
    private StudentService studentService;

    private final PasswordEncoder passwordEncoder;

    public SignUpController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup"; // Return the signup.html template
    }

    @PostMapping("/signup")
    public String processSignUp(
            @RequestParam String username,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword) {

        // Verify passwords match
        if (!password.equals(confirmPassword)) {
            // Redirect back to the sign-up page with an error
            return "redirect:/signup?error=passwordMismatch";
        }

        // Create and save the Student entity
        Student student = new Student();
        student.setUsername(username);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPassword(passwordEncoder.encode(password)); // Hash the password before saving
        student.setRole("ROLE_USER"); // Set default role
        student.setActive(true); // Default active to true
        student.setCreatedAt(LocalDateTime.now());

        // Save the student using the service layer
        studentService.saveStudent(student);

        return "redirect:/login"; // Redirect to login page after successful sign-up
    }
}