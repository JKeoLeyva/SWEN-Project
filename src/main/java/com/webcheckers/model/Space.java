package com.webcheckers.model;

public class Space {

    private int cellIdx;
    private Piece piece;
    private boolean isBlack;

    public Space(int row, int cellIdx, Piece piece) {
        if (cellIdx < 0 || cellIdx > 7) {
            throw new IllegalArgumentException("Index must be between 0-7");
        }
        this.cellIdx = cellIdx;
        this.piece = piece;
        this.isBlack = (row%2 != cellIdx%2);
    }

    public Space(int row, int cellIdx) {
        this(row, cellIdx, null);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getCellIdx() {
        return cellIdx;
    }

    public boolean isValid(int row) {
        return isBlack && (piece == null);
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return "[" + (this.piece == null ? " " : this.piece) + "]";
    }
}
