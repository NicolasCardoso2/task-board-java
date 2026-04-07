package br.com.seuprojeto.board.model;

public enum TaskStatus {
    TODO("A fazer"),
    IN_PROGRESS("Em andamento"),
    DONE("Concluído"),
    CANCELLED("Cancelado");

    private final String label;

    TaskStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
