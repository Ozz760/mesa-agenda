package com.mesa.agenda.todo.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Add this method to fetch todos by username with pagination
    Page<Todo> findByUsername(String username, Pageable pageable);
}
