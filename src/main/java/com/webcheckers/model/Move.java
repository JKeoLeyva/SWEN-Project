package com.webcheckers.model;

import com.webcheckers.appl.Message;

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

    @Override
    public String toString() {
        return "Move{" +
                "start=" + start +
                ", end=" + end +
                ", type=" + isJump +
                '}';
    }
}
