package com.mesa.agenda.todo.service;

import com.mesa.agenda.todo.domain.Todo;
import com.mesa.agenda.todo.domain.TodoRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TodoService {

    private final TodoRepository todoRepository;

    private final Clock clock;

    TodoService(TodoRepository todoRepository, Clock clock) {
        this.todoRepository = todoRepository;
        this.clock = clock;
    }

    public Todo createTodo(String description, @Nullable LocalDate dueDate) {
        if ("fail".equals(description)) {
            throw new RuntimeException("This is for testing the error handler");
        }
        var todo = new Todo();
        todo.setDescription(description);
        todo.setCreationDate(clock.instant());
        todo.setDueDate(dueDate);
        return todoRepository.saveAndFlush(todo);
    }

    public Todo readTodo(Long id) {
        var todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        return todo;
    }

    public Todo updateTodo(Long id, String description, @Nullable LocalDate dueDate) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        existingTodo.setDescription(description);
        existingTodo.setDueDate(dueDate);

        return todoRepository.save(existingTodo);
    }

    public void deleteTodo(Long id) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todoRepository.deleteById(id);
    }

    public List<Todo> list(Pageable pageable) {
        return todoRepository.findAllBy(pageable).toList();
    }

}
