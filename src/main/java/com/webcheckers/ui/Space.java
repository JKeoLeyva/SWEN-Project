package com.webcheckers.ui;

import com.webcheckers.model.Board;

public class Space {

    private int cellIdx;
    private Piece piece;
    private boolean isBlack;

    public Space(int row, int cellIdx, Piece piece) {
        if(cellIdx < 0 || cellIdx >= Board.BOARD_SIZE) {
            throw new IllegalArgumentException("Index must be between 0-7");
        }
        this.cellIdx = cellIdx;
        this.piece = piece;
        this.isBlack = (row % 2 != cellIdx % 2);
    }

    public int getCellIdx() {
        return cellIdx;
    }

    public boolean isValid() {
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
