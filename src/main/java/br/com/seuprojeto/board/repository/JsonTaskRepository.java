package br.com.seuprojeto.board.repository;

import br.com.seuprojeto.board.model.Task;
import br.com.seuprojeto.board.model.TaskStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Repositório com persistência em arquivo JSON (tasks.json).
 * Marcado como @Primary para que o Spring Boot injete esta implementação.
 * Também pode ser instanciado diretamente no modo console.
 */
@Primary
@Repository
public class JsonTaskRepository implements ITaskRepository {

    private static final String FILE_PATH = "tasks.json";

    private final ObjectMapper mapper;
    private final List<Task> storage;
    private final AtomicLong idGenerator;

    public JsonTaskRepository() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.storage = loadFromFile();
        long maxId = storage.stream().mapToLong(Task::getId).max().orElse(0L);
        this.idGenerator = new AtomicLong(maxId + 1);
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
            storage.add(task);
        } else {
            for (int i = 0; i < storage.size(); i++) {
                if (storage.get(i).getId().equals(task.getId())) {
                    storage.set(i, task);
                    persistToFile();
                    return task;
                }
            }
            storage.add(task);
        }
        persistToFile();
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
        boolean removed = storage.removeIf(t -> t.getId().equals(id));
        if (removed) persistToFile();
        return removed;
    }

    @Override
    public int count() {
        return storage.size();
    }

    // -------------------------------------------------------------------------

    private List<Task> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(file, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            System.err.println("Aviso: não foi possível carregar " + FILE_PATH + ". Iniciando com lista vazia.");
            return new ArrayList<>();
        }
    }

    private void persistToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), storage);
        } catch (IOException e) {
            System.err.println("Erro ao salvar " + FILE_PATH + ": " + e.getMessage());
        }
    }
}
