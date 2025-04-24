package com.mesa.agenda.todo.controller;
import com.mesa.agenda.todo.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mesa.agenda.todo.domain.Todo;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {

        Todo newTodo = todoService.createTodo(todo.getDescription(), todo.getDueDate());

        return ResponseEntity.status(201).body(newTodo);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

}
