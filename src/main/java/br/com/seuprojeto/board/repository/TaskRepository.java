package br.com.seuprojeto.board.repository;

import br.com.seuprojeto.board.model.Task;
import br.com.seuprojeto.board.model.TaskStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Repositório em memória para tarefas.
 * Útil para testes ou quando a persistência em arquivo não for necessária.
 */
@Repository
public class TaskRepository implements ITaskRepository {

    private final List<Task> storage = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
            storage.add(task);
            return task;
        }
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId().equals(task.getId())) {
                storage.set(i, task);
                return task;
            }
        }
        storage.add(task);
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return storage.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return storage.stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findByTitleContaining(String keyword) {
        String lower = keyword.toLowerCase(Locale.ROOT);
        return storage.stream()
                .filter(t -> t.getTitle().toLowerCase(Locale.ROOT).contains(lower))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Long id) {
        return storage.removeIf(t -> t.getId().equals(id));
    }

    @Override
    public int count() {
        return storage.size();
    }
}
