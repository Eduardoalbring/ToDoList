package com.eduardo.taskapi.controller;

import com.eduardo.taskapi.model.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.eduardo.taskapi.repository.TaskRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Task>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return repository.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task taskDetails) {

        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        task.setTitle(taskDetails.getTitle());
        task.setCompleted(taskDetails.isCompleted());

        return ResponseEntity.ok(repository.save(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        repository.delete(task);

        return ResponseEntity.noContent().build();
    }
}
