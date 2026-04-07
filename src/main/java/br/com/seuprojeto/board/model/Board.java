package br.com.seuprojeto.board.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Long id;
    private String name;
    private List<Column> columns;

    public Board(Long id, String name) {
        this.id = id;
        this.name = name;
        this.columns = new ArrayList<>();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Column> getColumns() { return columns; }

    public void addColumn(Column column) {
        columns.add(column);
    }

    @Override
    public String toString() {
        return String.format("Board: %s (%d coluna(s))", name, columns.size());
    }
}
