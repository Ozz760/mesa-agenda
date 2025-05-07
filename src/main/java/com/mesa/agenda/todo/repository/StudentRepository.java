package com.mesa.agenda.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mesa.agenda.todo.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);

    Optional<Student> findByEmail(String email);
}