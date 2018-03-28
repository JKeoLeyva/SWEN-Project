package com.webcheckers.model;

import java.util.Objects;

public class Move {

    private Position start;
    private Position end;
    private boolean isJump;

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
        this.isJump = false;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public boolean isJump() {
        return isJump;
    }

    // Note: only used for testing.
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return isJump == move.isJump &&
                Objects.equals(start, move.start) &&
                Objects.equals(end, move.end);
    }
}
