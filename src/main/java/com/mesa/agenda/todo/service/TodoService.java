package com.mesa.agenda.todo.service;

import java.time.Clock;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mesa.agenda.todo.domain.Todo;
import com.mesa.agenda.todo.domain.TodoRepository;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final Clock clock;

    public TodoService(TodoRepository todoRepository, Clock clock, StudentService studentService) {
        // 🟢 Constructor injection for dependencies

        this.todoRepository = todoRepository;
        this.clock = clock;
    }

    public Page<Todo> listForUser(String username, Pageable pageable) {
        return todoRepository.findByUsername(username, pageable);
    }

    public void createTodoForUser(String description, LocalDate dueDate, String username) {
        Todo todo = new Todo();
        todo.setDescription(description);
        todo.setDueDate(dueDate);
        todo.setUsername(username); // Set the username
        todo.setCreationDate(clock.instant());
        todoRepository.save(todo);
    }

    public Todo createTodo(String description, LocalDate dueDate) {
        Todo todo = new Todo();
        todo.setDescription(description);
        todo.setDueDate(dueDate);
        todo.setCreationDate(clock.instant());
        return todoRepository.save(todo);
    }

    public Page<Todo> list(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }
}
