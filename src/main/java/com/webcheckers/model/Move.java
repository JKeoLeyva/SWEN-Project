package com.webcheckers.model;

public class Move {
    private Position start;
    private Position end;
    private Piece piece;
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
     * @param piece the moving Piece
     */
    Move(Move move, Piece piece){
        this.start = move.getStart();
        this.end = move.getEnd();
        this.piece = piece;
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
        this.start = toReverse.end;
        this.end = toReverse.start;
        this.piece = toReverse.piece;
        this.type = toReverse.type;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    Move.Type getMoveType(){
        return type;
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
     * @return if the input move goes (dist) diagonally
     */
    private boolean isDiagonal(int dist){
        // If the Piece doesn't exist, it can't move.
        if(piece == null)
            return false;
        int rowStart = start.getRow();
        int colStart = start.getCell();
        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        int verticalMove = rowEnd - rowStart;
        // Red pieces move in the negative direction.
        if(piece.getColor() == Piece.Color.RED)
            verticalMove *= -1;
        // Kings can move in either direction.
        if(piece.getType() == Piece.Type.KING)
            verticalMove = Math.abs(verticalMove);
        int horizontalMove = Math.abs(colStart - colEnd);
        return horizontalMove == dist && verticalMove == dist;
    }


    // Note: only used for testing.
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return start.equals(move.start) && end.equals(move.end);
    }
}
