package com.webcheckers.model;

import com.webcheckers.appl.Message;

public class ValidMoves {
    private static final String INVALID_COLOR = "You can't move onto white squares.";
    private static final String INVALID_DISTANCE = "You can only move forward 1 space diagonally.";
    private static final String TAKEN_SPACE = "You can only move onto an empty space.";
    private static final String VALID_MOVE = "Move is valid!";

    public static Message isValid(Move move, Board board){
        if(colorIsInvalid(move))
            return new Message(INVALID_COLOR, Message.Type.error);
        else if(distanceIsInvalid(move, board))
            return new Message(INVALID_DISTANCE, Message.Type.error);
        else if (spaceIsTaken(move, board))
            return new Message(TAKEN_SPACE, Message.Type.error);
        else
            return new Message(VALID_MOVE, Message.Type.info);
    }

    // Pieces can only move onto black spaces.
    private static boolean colorIsInvalid(Move move){
        int rowEnd = move.getEnd().getRow();
        int colEnd = move.getEnd().getCol();
        return (rowEnd%2 == colEnd%2);
    }

    // Assumes a SINGLE Piece.
    // From the perspective of a Board, red pieces can move down,
    // and white pieces can move up.
    private static boolean distanceIsInvalid(Move move, Board board){
        int rowStart = move.getStart().getRow();
        int colStart = move.getStart().getCol();
        int rowEnd = move.getEnd().getRow();
        int colEnd = move.getEnd().getCol();
        Piece moving = board.getPiece(rowStart, colStart);
        int verticalMove = rowStart - rowEnd;
        int horizontalMove = Math.abs(colStart - colEnd);

        return horizontalMove != 1 || (moving.getColor() == Piece.Color.RED ?
                verticalMove != 1 : verticalMove != -1);
    }

    private static boolean spaceIsTaken(Move move, Board board){
        int rowEnd = move.getEnd().getRow();
        int colEnd = move.getEnd().getCol();
        Piece curr = board.getPiece(rowEnd, colEnd);
        return (curr != null);
    }
}
