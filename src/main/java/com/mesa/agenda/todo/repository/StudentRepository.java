package com.mesa.agenda.todo.repository;

import com.mesa.agenda.todo.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Optional: custom query methods like:
    Student findByUsername(String username);
}