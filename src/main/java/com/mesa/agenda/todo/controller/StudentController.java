package com.mesa.agenda.todo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mesa.agenda.todo.domain.Student;
import com.mesa.agenda.todo.service.StudentService;

@RestController
@RequestMapping("rest/students")
@CrossOrigin(origins = "http://localhost:3000") // Adjust the origin as needed
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(produces = "application/json")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(value = "/html", produces = "text/html")
    public ResponseEntity<String> getAllStudentsAsHtml() {
        List<Student> students = studentService.getAllStudents();
        StringBuilder htmlResponse = new StringBuilder("<html><body><h1>Students</h1><ul>");

        for (Student student : students) {
            htmlResponse.append("<li>")
                    .append("ID: ").append(student.getId()).append("<br>")
                    .append("Name: ").append(student.getFirstName()).append(" ").append(student.getLastName())
                    .append("<br>")
                    .append("Email: ").append(student.getEmail()).append("</li><br>");
        }

        htmlResponse.append("</ul></body></html>");
        return ResponseEntity.ok().header("Content-Type", "text/html").body(htmlResponse.toString());
    }

    @GetMapping(value = "/json", produces = "application/json")
    public List<Student> getAllStudentsAsJson() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
}
