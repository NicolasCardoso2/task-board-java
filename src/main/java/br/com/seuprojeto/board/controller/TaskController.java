package br.com.seuprojeto.board.controller;

import br.com.seuprojeto.board.dto.TaskDTO;
import br.com.seuprojeto.board.model.Priority;
import br.com.seuprojeto.board.model.Task;
import br.com.seuprojeto.board.model.TaskStatus;
import br.com.seuprojeto.board.service.TaskService;
import br.com.seuprojeto.board.util.InputUtil;

import java.util.List;

/**
 * Camada de controle: gerencia a interação com o usuário via terminal.
 * Delega toda lógica de negócio ao TaskService.
 */
public class TaskController {

    private final TaskService taskService;
    private final InputUtil inputUtil;

    public TaskController(TaskService taskService, InputUtil inputUtil) {
        this.taskService = taskService;
        this.inputUtil = inputUtil;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int option = inputUtil.readInt("Escolha uma opção: ");
            switch (option) {
                case 1:
                    handleCreateTask();
                    break;
                case 2:
                    handleListAll();
                    break;
                case 3:
                    handleListByStatus();
                    break;
                case 4:
                    handleSearch();
                    break;
                case 5:
                    handleUpdateTask();
                    break;
                case 6:
                    handleChangeStatus();
                    break;
                case 7:
                    handleDeleteTask();
                    break;
                case 0:
                    System.out.println("\nAté logo!");
                    running = false;
                    break;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
            }
        }
        inputUtil.close();
    }

    private void printMenu() {
        System.out.println("\n========== BOARD DE TAREFAS ==========");
        System.out.println("  1. Nova tarefa");
        System.out.println("  2. Listar todas as tarefas");
        System.out.println("  3. Filtrar por status");
        System.out.println("  4. Buscar por nome");
        System.out.println("  5. Editar tarefa");
        System.out.println("  6. Alterar status");
        System.out.println("  7. Remover tarefa");
        System.out.println("  0. Sair");
        System.out.println("=======================================");
    }

    private void handleCreateTask() {
        System.out.println("\n-- Nova Tarefa --");
        String title = inputUtil.readLine("Título: ");
        String description = inputUtil.readLine("Descrição: ");
        Priority priority = selectPriority();
        try {
            Task task = taskService.createTask(new TaskDTO(title, description, priority));
            System.out.println("Tarefa criada: " + task);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void handleListAll() {
        List<Task> tasks = taskService.listAll();
        System.out.println("\n-- Todas as Tarefas (" + tasks.size() + ") --");
        if (tasks.isEmpty()) {
            System.out.println("  Nenhuma tarefa cadastrada.");
        } else {
            printHeader();
            tasks.forEach(t -> System.out.println("  " + t));
        }
    }

    private void handleListByStatus() {
        System.out.println("\nStatus disponíveis:");
        for (TaskStatus s : TaskStatus.values()) {
            System.out.println("  " + s.ordinal() + " - " + s.getLabel());
        }
        int choice = inputUtil.readInt("Escolha o status: ");
        TaskStatus[] values = TaskStatus.values();
        if (choice < 0 || choice >= values.length) {
            System.out.println("Status inválido.");
            return;
        }
        TaskStatus status = values[choice];
        List<Task> tasks = taskService.listByStatus(status);
        System.out.println("\n-- " + status.getLabel() + " (" + tasks.size() + ") --");
        if (tasks.isEmpty()) {
            System.out.println("  Nenhuma tarefa com este status.");
        } else {
            printHeader();
            tasks.forEach(t -> System.out.println("  " + t));
        }
    }

    private void handleSearch() {
        String keyword = inputUtil.readLine("Buscar por título (parcial): ");
        List<Task> tasks = taskService.searchByTitle(keyword);
        System.out.println("\n-- Resultados para \"" + keyword + "\" (" + tasks.size() + ") --");
        if (tasks.isEmpty()) {
            System.out.println("  Nenhuma tarefa encontrada.");
        } else {
            printHeader();
            tasks.forEach(t -> System.out.println("  " + t));
        }
    }

    private void handleUpdateTask() {
        long id = inputUtil.readLong("ID da tarefa a editar: ");
        try {
            String title = inputUtil.readLine("Novo título: ");
            String description = inputUtil.readLine("Nova descrição: ");
            Priority priority = selectPriority();
            Task task = taskService.updateTask(id, new TaskDTO(title, description, priority));
            System.out.println("Tarefa atualizada: " + task);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void handleChangeStatus() {
        long id = inputUtil.readLong("ID da tarefa: ");
        System.out.println("Novo status:");
        for (TaskStatus s : TaskStatus.values()) {
            System.out.println("  " + s.ordinal() + " - " + s.getLabel());
        }
        int choice = inputUtil.readInt("Escolha o status: ");
        TaskStatus[] values = TaskStatus.values();
        if (choice < 0 || choice >= values.length) {
            System.out.println("Status inválido.");
            return;
        }
        try {
            Task task = taskService.changeStatus(id, values[choice]);
            System.out.println("Status alterado: " + task);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void handleDeleteTask() {
        long id = inputUtil.readLong("ID da tarefa a remover: ");
        String confirm = inputUtil.readLine("Confirma remoção? (s/N): ");
        if (!confirm.equalsIgnoreCase("s")) {
            System.out.println("Operação cancelada.");
            return;
        }
        try {
            taskService.deleteTask(id);
            System.out.println("Tarefa removida com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------

    private Priority selectPriority() {
        System.out.println("Prioridade:");
        for (Priority p : Priority.values()) {
            System.out.println("  " + p.ordinal() + " - " + p.getLabel());
        }
        int choice = inputUtil.readInt("Escolha (padrão MEDIUM=1): ");
        Priority[] values = Priority.values();
        if (choice < 0 || choice >= values.length) {
            System.out.println("  Prioridade inválida, usando MEDIUM.");
            return Priority.MEDIUM;
        }
        return values[choice];
    }

    private void printHeader() {
        System.out.printf("  %-4s %-30s %-14s %-10s %s%n",
                "ID", "Título", "Status", "Prioridade", "Descrição");
        System.out.println("  " + "-".repeat(80));
    }
}
