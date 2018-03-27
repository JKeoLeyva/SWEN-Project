package com.webcheckers.model;

import com.webcheckers.appl.Message;

import java.util.Collections;
import java.util.Stack;

public class StableTurn {
    private static final String MOVE_ALREADY_MADE = "Backup to make another move.";
    private static final String INVALID_DISTANCE = "You can only move forward 1 space diagonally.";
    private static final String TAKEN_SPACE = "You can only move onto an empty space.";
    private static final String VALID_MOVE = "Move is valid!";
    private Stack<Move> validatedMoves;
    private Board temp;

    public StableTurn(Board board){
        temp = new Board();
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++){
                Piece curr = board.getPiece(row, col);
                temp.setPiece(row, col, curr);
            }
        }
        validatedMoves = new Stack<>();
    }

    public Message isValid(Move move) {
        if( !validatedMoves.empty() && !validatedMoves.peek().isJump() )
            return new Message(MOVE_ALREADY_MADE, Message.Type.error);
        else if(distanceIsInvalid(move))
            return new Message(INVALID_DISTANCE, Message.Type.error);
        else if (spaceIsTaken(move))
            return new Message(TAKEN_SPACE, Message.Type.error);
        else {
            validatedMoves.push(move);
            temp.makeMove(move);
            return new Message(VALID_MOVE, Message.Type.info);
        }

        //System.out.println(validatedMoves.toString());
    }

    // Assumes a SINGLE Piece.
    // From the perspective of a Board, red pieces can move down,
    // and white pieces can move up.
    private boolean distanceIsInvalid(Move move){
        Position start = move.getStart();
        Position end = move.getEnd();

        int rowStart = start.getRow();
        int colStart = start.getCell();
        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        Piece moving = temp.getPiece(rowStart, colStart);
        int verticalMove = rowStart - rowEnd;
        int horizontalMove = Math.abs(colStart - colEnd);

        return horizontalMove != 1 || (moving.getColor() == Piece.Color.RED ?
                verticalMove != 1 : verticalMove != -1);
    }

    private boolean spaceIsTaken(Move move){
        Position end = move.getEnd();

        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        Piece curr = temp.getPiece(rowEnd, colEnd);
        return (curr != null);
    }

    public void backupMove(){
        Move toReverse = validatedMoves.pop();
        Move reversed = new Move(toReverse.getEnd(), toReverse.getStart());
        temp.makeMove(reversed);
    }

    public Stack<Move> getValidatedMoves(){
        Collections.reverse(validatedMoves);
        return validatedMoves;
    }
}
