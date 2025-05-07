package com.mesa.agenda.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mesa.agenda.todo.domain.Student;
import com.mesa.agenda.todo.repository.StudentRepository;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveStudent(Student student) {
        if (student.getRole() == null || student.getRole().isEmpty()) {
            student.setRole("ROLE_USER"); // Set default role if not provided
        }
        student.setPassword(passwordEncoder.encode(student.getPassword())); // Encrypt password
        studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }

    public Optional<Student> updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(existing -> {
            existing.setFirstName(updatedStudent.getFirstName());
            existing.setLastName(updatedStudent.getLastName());
            existing.setEmail(updatedStudent.getEmail());
            if (updatedStudent.getPassword() != null && !updatedStudent.getPassword().isEmpty()) {
                existing.setPassword(passwordEncoder.encode(updatedStudent.getPassword())); // Encrypt password
            }
            if (updatedStudent.getRole() == null || updatedStudent.getRole().isEmpty()) {
                existing.setRole("ROLE_USER"); // Set default role if not provided
            } else {
                existing.setRole(updatedStudent.getRole());
            }
            return studentRepository.save(existing);
        });
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
