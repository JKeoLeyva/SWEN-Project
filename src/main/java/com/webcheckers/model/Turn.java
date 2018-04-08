package com.webcheckers.model;

import com.webcheckers.appl.Message;

import java.util.*;

public class Turn {

    // Some Strings for displaying the results of trying to validate moves.
    static final String BAD_MOVE = "ERROR! Move is out of board range.";
    static final String MOVE_ALREADY_MADE = "Backup to make another move.";
    static final String INVALID_DISTANCE = "You can only move forward 1 space diagonally or make any number of valid jumps.";
    static final String ALREADY_JUMPING = "You can't make a single space move after jumping.";
    static final String BAD_JUMP = "You must jump over a piece of the opposite color.";
    static final String JUMP_CHANGE = "You cannot jump with two different pieces in the same turn.";
    static final String JUMPED_OVER = "Piece was already jumped over.";
    static final String VALID_MOVE = "Move is valid!";

    // A Stack to store validated moves.
    private Stack<Move> validatedMoves = new Stack<>();
    // A set to store spaces jumped over.
    private Set<Position> jumpedSpaces = new HashSet<>();
    // A temporary Board to store moves.
    private Board temp;
    // If the turn has jumps (and therefore can't have any more single moves)
    private Piece.Color playerColor;

    Turn(Board board, Piece.Color playerColor) {
        this.playerColor = playerColor;
        this.temp = new Board(board);
    }

    /**
     * Determines if a move is valid, and returns the proper message.
     * Makes the move on the temporary board if move is valid.
     * @param move to be validated
     * @return Message to be displayed
     */
    Message tryMove(Move move) {
        if(temp.outOfBounds(move.getStart()) || temp.outOfBounds(move.getEnd()))
            return new Message(BAD_MOVE, Message.Type.error);

        move = new Move(move, temp.getPiece(move.getStart()));
        if(move.getMoveType() == Move.Type.INVALID)
            return new Message(INVALID_DISTANCE, Message.Type.error);

        Move.Type prevType = validatedMoves.empty() ? null : validatedMoves.peek().getMoveType();
        if(prevType == Move.Type.SINGLE)
            return new Message(MOVE_ALREADY_MADE, Message.Type.error);

        else if (prevType == Move.Type.JUMP){
            if(move.getMoveType() == Move.Type.SINGLE)
                return new Message(ALREADY_JUMPING, Message.Type.error);
            if(jumpedSpaces.contains(move.getJumped()))
                return new Message(JUMPED_OVER, Message.Type.error);
            if(!validatedMoves.peek().getEnd().equals(move.getStart())){
                return new Message(JUMP_CHANGE, Message.Type.error);}
        }

        if(move.getMoveType() == Move.Type.JUMP && isInvalidJumpMove(move))
            return new Message(BAD_JUMP, Message.Type.error);

        validatedMoves.push(move);
        makeMove(move, false);
        return new Message(VALID_MOVE, Message.Type.info);
    }

    /**
     * @param move to be tested
     * @return if the jump moves jumps over a valid piece
     */
    private boolean isInvalidJumpMove(Move move){
        Position jumpedPos = move.getJumped();
        Piece jumped = temp.getPiece(jumpedPos);
        return jumped == null || jumped.getColor() == playerColor;
    }

    /**
     * Reverses the last move made, and removes it from the Move stack.
     */
    void backupMove(){
        Move toReverse = validatedMoves.pop();
        Move reversed = new Move(toReverse);
        makeMove(reversed, true);
    }

    /**
     * Makes the given move on the temp board.
     * @param move to be made
     * @param reversed if true, it's a jump move that's being reversed, and jumpedSpaces changes accordingly.
     */
    private void makeMove(Move move, boolean reversed){
        Piece moving = temp.getPiece(move.getStart());
        temp.setPiece(move.getStart(), null);
        temp.setPiece(move.getEnd(), moving);
        if(reversed)
            jumpedSpaces.remove(move.getJumped());
        else
            jumpedSpaces.add(move.getJumped());
    }

    /**
     * Empties out and returns the validated moves.
     * @return A Stack of moves with the first move on top, and so on.
     */
    Stack<Move> getValidatedMoves(){
        Stack<Move> ret = new Stack<>();
        while(!validatedMoves.empty())
            ret.push(validatedMoves.pop());

        return ret;
    }

    void setPlayerColor(Piece.Color playerColor) {
        this.playerColor = playerColor;
    }
}
