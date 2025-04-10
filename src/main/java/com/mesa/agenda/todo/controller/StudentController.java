package com.mesa.agenda.todo.controller;

import com.mesa.agenda.todo.domain.Student;
import com.mesa.agenda.todo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get a single student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    // Create a new student
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // Update a student
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updated) {
        Student student = studentRepository.findById(id).orElseThrow();
        student.setStudentName(updated.getStudentName());
        student.setUsername(updated.getUsername());
        student.setEmail(updated.getEmail());
        student.setPassword(updated.getPassword());
        student.setRole(updated.getRole());
        student.setActive(updated.isActive());
        return studentRepository.save(student);
    }

    // Delete a student by ID
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
