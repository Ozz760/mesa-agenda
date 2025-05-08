package com.mesa.agenda.todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

//    private final TaskService taskService;
//
//    public TaskController(TaskService taskService) { this.taskService = taskService; }

    // GET /students/{studentId}/classes/{classId}/tasks/{taskId}
    //get one specific task from only one class
//    @GetMapping("/{taskId}")
//    public ResponseEntity<Task> getTaskById(
//            @PathVariable Long studentId,
//            @PathVariable Long classId,
//            @PathVariable Long taskId) {
//
//        // Your service layer should verify that:
//        // 1. The class with classId belongs to the student with studentId
//        // 2. The task with taskId belongs to that class
//
//        return taskService.getTaskById(studentId, classId, taskId)
//                .map(ResponseEntity.ok(task))
//                .orElse(ResponseEntity.notFound().build());
//    }

    // GET /students/{studentId}/classes/{classId}/tasks/
    //get all the tasks associated with the specific class
//    @GetMapping
//    public ResponseEntity<Task> getAllTasksByClassId(
//            @PathVariable Long studentId,
//            @PathVariable Long classId) {
//
//        // Your service layer should verify that:
//        // 1. The class with classId belongs to the student with studentId
//
//        List<Task> tasks = taskService.findAllTasksByStudentAndClass(studentId, classId);
//        if (tasks.isEmpty()) {
//            return ResponseEntity.noContent().build(); // Or .ok(Collections.emptyList())
//        } else {
//            return ResponseEntity.ok(tasks); // 200 OK with list in body
//        }
//    }

    //get all tasks for a student
    //might belong in student controller instead
    //    @GetMapping("/rest/students/{studentId}")
    //    public ResponseEntity<Task> getAllTasksByStudentId(@PathVariable Long id) {
    //        return taskService.getAllTasksByStudentID(id)
    //                .map(ResponseEntity::ok)
    //                .orElse(ResponseEntity.notFound().build());
    //    }


}
