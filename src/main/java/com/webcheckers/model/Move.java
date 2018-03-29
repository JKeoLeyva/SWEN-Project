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

    public Position getJumpPosition(Piece.Color playerColor) {
        int rowStart = start.getRow();
        int colStart = start.getCell();
        int colEnd = end.getCell();
        int jumpRow = playerColor == Piece.Color.RED ? rowStart - 1 : rowStart + 1;
        int jumpCol = colEnd - colStart > 0 ? colStart + 1 : colStart - 1;

        return new Position(jumpRow, jumpCol);
    }

    public boolean isJumpMove(Board board, Piece.Color playerColor) {
        // Get indices
        int rowStart = start.getRow();
        int colStart = start.getCell();
        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        int verticalMove = rowEnd - rowStart;
        int horizontalMove = Math.abs(colStart - colEnd);
        Position jumpPosition = getJumpPosition(playerColor);

        // Check to make sure there's a piece of the opposite color that's being jumped
        Piece piece = board.getPiece(jumpPosition.getRow(), jumpPosition.getCell());
        if(piece == null || piece.getColor() == playerColor)
            return false;

        // Assumes a Jump move.
        // From the perspective of a Board, red pieces can move down,
        // and white pieces can move up.
        return horizontalMove == 2 && (playerColor == Piece.Color.RED ?
                verticalMove == -2 : verticalMove == 2);
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
