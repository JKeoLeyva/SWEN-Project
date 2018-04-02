package com.webcheckers.model;

import java.util.Objects;

public class Move {
    private Position start;
    private Position end;
    private Piece.Color color;
    private Type type;

    // Note: this only shows what the jump distance is.
    enum Type {
        INVALID, SINGLE, JUMP
    }

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Initializes a Move with additional information.
     * @param move the move to copy start and end from
     * @param color the color of the moving piece
     */
    Move(Move move, Piece.Color color){
        this.start = move.getStart();
        this.end = move.getEnd();
        this.color = color;
        if(isDiagonal(1))
            type = Type.SINGLE;
        else if (isDiagonal(2))
            type = Type.JUMP;
        else
            type = Type.INVALID;
    }

    /**
     * Creates the reverse of a given Move.
     * @param toReverse the Move to reverse
     */
    Move(Move toReverse){
        this.start = toReverse.getEnd();
        this.end = toReverse.getStart();
        this.color = toReverse.getColor();
        this.type = toReverse.getMoveType();
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
