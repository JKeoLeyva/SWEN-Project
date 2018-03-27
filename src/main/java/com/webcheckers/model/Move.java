package com.webcheckers.model;

import com.webcheckers.appl.Message;

public class Move {
    private static final String INVALID_DISTANCE = "You can only move forward 1 space diagonally.";
    private static final String TAKEN_SPACE = "You can only move onto an empty space.";
    private static final String VALID_MOVE = "Move is valid!";

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

    public Message isValid(Board board){
        if(distanceIsInvalid(board))
            return new Message(INVALID_DISTANCE, Message.Type.error);
        else if (spaceIsTaken(board))
            return new Message(TAKEN_SPACE, Message.Type.error);
        else
            return new Message(VALID_MOVE, Message.Type.info);
    }

    // Assumes a SINGLE Piece.
    // From the perspective of a Board, red pieces can move down,
    // and white pieces can move up.
    private boolean distanceIsInvalid(Board board){
        int rowStart = start.getRow();
        int colStart = start.getCell();
        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        Piece moving = board.getPiece(rowStart, colStart);
        int verticalMove = rowStart - rowEnd;
        int horizontalMove = Math.abs(colStart - colEnd);

        return horizontalMove != 1 || (moving.getColor() == Piece.Color.RED ?
                verticalMove != 1 : verticalMove != -1);
    }

    private boolean spaceIsTaken(Board board){
        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        Piece curr = board.getPiece(rowEnd, colEnd);
        return (curr != null);
    }


}
