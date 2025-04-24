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

        return ResponseEntity.status(201).body(newTodo); //201 created
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> readTodo(@PathVariable Long id) {
        try {
            Todo todo = todoService.readTodo(id);
            return ResponseEntity.ok(todo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todo.getDescription(), todo.getDueDate());
            return ResponseEntity.ok(updatedTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build(); //404 not found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
