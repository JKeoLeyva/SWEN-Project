package com.webcheckers.model;

/**
 * A class to represent a single checkers square.
 */
public class Space {

    private int row;
    private int cellIdx;
    private Piece piece;
    private boolean isBlack;

    /**
     * Create a new Space (or board cell)
     *
     * @param row     the row of the space
     * @param cellIdx the cell index in the board (column)
     * @param piece   the piece in the space
     * @throws IllegalArgumentException if the index is not in 0-7
     */
    public Space(int row, int cellIdx, Piece piece) {
        if(cellIdx < 0 || cellIdx >= Board.BOARD_SIZE) {
            throw new IllegalArgumentException("Index must be between 0-7");
        }
        this.row = row;
        this.cellIdx = cellIdx;
        this.piece = piece;
        // Encapsulates logic of board square coloring.
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

    public int getRow() {
        return row;
    }

    /**
     * @return this Space as a Position
     */
    public Position asPosition() {
        return new Position(row, cellIdx);
    }

    // For potential testing purposes.
    @Override
    public String toString() {
        return "[" + (this.piece == null ? " " : this.piece) + "] (" + row + ", " + cellIdx + ")";
    }
}
