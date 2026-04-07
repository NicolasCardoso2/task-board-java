package br.com.seuprojeto.board.controller;

import br.com.seuprojeto.board.dto.TaskDTO;
import br.com.seuprojeto.board.model.Priority;
import br.com.seuprojeto.board.model.Task;
import br.com.seuprojeto.board.model.TaskStatus;
import br.com.seuprojeto.board.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller — Level 4
 *
 * GET    /tasks              → lista todas
 * GET    /tasks/{id}         → busca por ID
 * GET    /tasks/search?title → busca por título
 * GET    /tasks?status=      → filtra por status
 * POST   /tasks              → cria tarefa
 * PUT    /tasks/{id}         → atualiza título, descrição e prioridade
 * PATCH  /tasks/{id}/status  → altera status
 * DELETE /tasks/{id}         → remove tarefa
 */
@RestController
@RequestMapping("/tasks")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> findAll(@RequestParam(required = false) TaskStatus status) {
        if (status != null) {
            return taskService.listByStatus(status);
        }
        return taskService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        return taskService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Task> search(@RequestParam String title) {
        return taskService.searchByTitle(title);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@RequestBody TaskDTO dto) {
        return taskService.createTask(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody TaskDTO dto) {
        Task updated = taskService.updateTask(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> changeStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {
        Task updated = taskService.changeStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<Task> changePriority(
            @PathVariable Long id,
            @RequestParam Priority priority) {
        Task updated = taskService.changePriority(id, priority);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
