package br.com.seuprojeto.board.repository;

import br.com.seuprojeto.board.model.Task;
import br.com.seuprojeto.board.model.TaskStatus;

import java.util.List;
import java.util.Optional;

/**
 * Contrato do repositório de tarefas.
 * Permite trocar a implementação (memória, arquivo, banco) sem alterar o serviço.
 */
public interface ITaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleContaining(String keyword);

    boolean deleteById(Long id);

    int count();
}
