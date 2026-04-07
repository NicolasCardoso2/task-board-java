package br.com.seuprojeto.board.model;

import java.util.ArrayList;
import java.util.List;

public class Column {

    private Long id;
    private String name;
    private List<Task> tasks;

    public Column(Long id, String name) {
        this.id = id;
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Task> getTasks() { return tasks; }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    @Override
    public String toString() {
        return String.format("Coluna: %s (%d tarefa(s))", name, tasks.size());
    }
}
