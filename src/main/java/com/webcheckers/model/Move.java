package com.webcheckers.model;

import java.util.Objects;

public class Move {
    private Position start;
    private Position end;
    private Piece.Color color;
    private Type type;

    // Note: this only shows what the jump distance is
    enum Type {
        INVALID, SINGLE, JUMP
    }

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

    public Move.Type getMoveType(){
        return type;
    }

    public Piece.Color getColor() {
        return color;
    }

    /**
     * Acts as a pseudo-constructor. Sets up some Move attributes.
     * @param color of the moving piece.
     */
    public void moveSetup(Piece.Color color){
        this.color = color;
        if(isDiagonal(1))
            type = Type.SINGLE;
        else if (isDiagonal(2))
            type = Type.JUMP;
        else
            type = Type.INVALID;
    }

    /**
     * @return the Position this move jumped over, or null if it was not a jump move.
     */
    public Position getJumped(){
        if(type != Type.JUMP)
            return null;
        int startRow = start.getRow();
        int endRow = end.getRow();
        int startCol = start.getCell();
        int endCol = end.getCell();
        return new Position((startRow + endRow)/2, (startCol + endCol)/2);
    }

    /**
     * @param dist distance to check
     * @return if the inout moves goes (dist) diagonally
     */
    private boolean isDiagonal(int dist){
        int rowStart = start.getRow();
        int colStart = start.getCell();
        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        int verticalMove = rowEnd - rowStart;
        int horizontalMove = Math.abs(colStart - colEnd);

        // Assumes a SINGLE Piece.
        // From the perspective of a Board, red pieces can move down,
        // and white pieces can move up.
        return horizontalMove == dist && (color == Piece.Color.RED ?
                verticalMove == -dist : verticalMove == dist);
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
