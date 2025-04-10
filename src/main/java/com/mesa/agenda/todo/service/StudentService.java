package com.mesa.agenda.todo.service;

import com.mesa.agenda.todo.domain.Student;
import com.mesa.agenda.todo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(existing -> {
            existing.setFirstName(updatedStudent.getFirstName());
            existing.setLastName(updatedStudent.getLastName());
            existing.setEmail(updatedStudent.getEmail());
            return studentRepository.save(existing);
        });
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
