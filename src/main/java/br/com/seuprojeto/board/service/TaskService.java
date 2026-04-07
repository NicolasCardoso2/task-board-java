package br.com.seuprojeto.board.service;

import br.com.seuprojeto.board.dto.TaskDTO;
import br.com.seuprojeto.board.model.Priority;
import br.com.seuprojeto.board.model.Task;
import br.com.seuprojeto.board.model.TaskStatus;
import br.com.seuprojeto.board.repository.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço: contém as regras de negócio relacionadas a tarefas.
 */
@Service
public class TaskService {

    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskDTO dto) {
        validateDTO(dto);
        Priority priority = dto.getPriority() != null ? dto.getPriority() : Priority.MEDIUM;
        Task task = new Task(null, dto.getTitle().trim(), dto.getDescription().trim(), priority);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, TaskDTO dto) {
        validateDTO(dto);
        Task task = findOrThrow(id);
        task.setTitle(dto.getTitle().trim());
        task.setDescription(dto.getDescription().trim());
        if (dto.getPriority() != null) {
            task.setPriority(dto.getPriority());
        }
        return taskRepository.save(task);
    }

    public Task changeStatus(Long id, TaskStatus newStatus) {
        Task task = findOrThrow(id);
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }

    public Task changePriority(Long id, Priority newPriority) {
        Task task = findOrThrow(id);
        task.setPriority(newPriority);
        return taskRepository.save(task);
    }

    public boolean deleteTask(Long id) {
        findOrThrow(id);
        return taskRepository.deleteById(id);
    }

    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    public List<Task> listByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> searchByTitle(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return taskRepository.findAll();
        }
        return taskRepository.findByTitleContaining(keyword.trim());
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    private Task findOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada com ID: " + id));
    }

    private void validateDTO(TaskDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Os dados da tarefa não podem ser nulos.");
        }
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("O título da tarefa é obrigatório.");
        }
    }
}
