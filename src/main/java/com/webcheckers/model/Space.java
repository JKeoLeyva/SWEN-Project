package com.webcheckers.model;

public class Space {

    private int cellIdx;
    private Piece piece;

    public Space(int cellIdx) {
        if (cellIdx < 0 || cellIdx > 7) {
            throw new IllegalArgumentException("Index must be between 0-7");
        }

        this.cellIdx = cellIdx;
    }

    public Space(int cellIdx, Piece piece) {
        if (cellIdx < 0 || cellIdx > 7) {
            throw new IllegalArgumentException("Index must be between 0-7");
        }

        this.cellIdx = cellIdx;
        this.piece = piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isValid(int row) {
        return (row % 2 == cellIdx % 2) && (piece == null);
    }

    public int getCellIdx() {
        return cellIdx;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return "[" + (this.piece == null ? " " : this.piece) + "]";
    }
}
