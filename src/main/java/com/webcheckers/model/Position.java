package com.webcheckers.model;

import java.util.Objects;

public class Position {
    private int row;
    private int cell;

    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                cell == position.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, cell);
    }
}
