package br.com.seuprojeto.board.model;

public enum Priority {
    LOW("Baixa"),
    MEDIUM("Média"),
    HIGH("Alta");

    private final String label;

    Priority(String label) {
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
