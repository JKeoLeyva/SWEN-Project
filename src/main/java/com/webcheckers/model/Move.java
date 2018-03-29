package com.webcheckers.model;

import java.util.Objects;

public class Move {

    private Position start;
    private Position end;

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    // Note: only used for testing.
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(start, move.start) &&
                Objects.equals(end, move.end);
    }
}
