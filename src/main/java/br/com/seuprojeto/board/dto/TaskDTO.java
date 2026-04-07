package br.com.seuprojeto.board.dto;

import br.com.seuprojeto.board.model.Priority;

/**
 * DTO (Data Transfer Object) usado para transportar dados de entrada
 * da camada de controller para a camada de service.
 */
public class TaskDTO {

    private String title;
    private String description;
    private Priority priority;

    public TaskDTO() {}

    public TaskDTO(String title, String description) {
        this.title = title;
        this.description = description;
        this.priority = Priority.MEDIUM;
    }

    public TaskDTO(String title, String description, Priority priority) {
        this.title = title;
        this.description = description;
        this.priority = priority != null ? priority : Priority.MEDIUM;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(Priority priority) { this.priority = priority; }
}
