package com.webcheckers.model;

import com.webcheckers.appl.Message;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Turn {

    // Some Strings for displaying the results of trying to validate moves.
    static final String BAD_MOVE = "ERROR! Move is out of board range.";
    static final String MOVE_ALREADY_MADE = "Backup to make another move.";
    static final String INVALID_DISTANCE = "You can only move forward 1 space diagonally or make any number of valid jumps.";
    static final String ALREADY_JUMPING = "You can't make a single space move after jumping.";
    static final String BAD_JUMP = "You must jump over a piece of the opposite color.";
    static final String JUMP_CHANGE = "You cannot jump with two different pieces in the same turn.";
    static final String JUMPED_OVER = "Piece was already jumped over.";
    static final String SPACE_OCCUPIED = "ERROR! Tried to move onto an occupied space.";
    static final String VALID_MOVE = "Move is valid!";
    static final String FORCED_MOVE = "Jump moves are available, so no single move can be taken.";

    // A Stack to store validated moves.
    private Stack<Move> validatedMoves = new Stack<>();
    // A set to store spaces jumped over.
    private Set<Position> jumpedSpaces = new HashSet<>();
    // A temporary Board to store moves.
    private Board temp;
    // The color of the player whose turn it is.
    private Piece.Color playerColor;
    // An always-updating Set of possible valid moves.
    private Set<Move> possibleMoves;
    // If possibleMoves contains a Jump move.
    private boolean jumpMoveExists = false;

    Turn(Board board, Piece.Color playerColor) {
        this.temp = new Board(board);
        this.playerColor = playerColor;
        this.possibleMoves = generateValidMoves();
    }

    /**
     * Determines if a move is valid, and returns the proper message.
     * @param move to be validated
     * @return Message to be displayed
     */
    private Message testMove(Move move) {

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

        if(temp.getPiece(move.getEnd()) != null)
            return new Message(SPACE_OCCUPIED, Message.Type.error);

        return new Message(VALID_MOVE, Message.Type.info);
    }

    /**
     * Tests if a move is valid, and makes the move on the temp board if it is.
     * @param move to try
     * @return proper message
     */
    Message tryMove(Move move){
        // If Move is out of bounds, it is not even tested.
        if(temp.outOfBounds(move.getStart()) || temp.outOfBounds(move.getEnd()))
            return new Message(BAD_MOVE, Message.Type.error);

        move = new Move(move, temp.getPiece(move.getStart()));
        Message ret = testMove(move);
        if(ret.getType() == Message.Type.error)
            return ret;

        // If a jump move exists, the player is forced to make it.
        if(move.getMoveType() == Move.Type.SINGLE && jumpMoveExists)
            return new Message(FORCED_MOVE, Message.Type.error);

        validatedMoves.add(move);
        makeMove(move, false);
        possibleMoves = generateValidMoves();
        return ret;
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
        possibleMoves = generateValidMoves();
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
     *         If there is still a forced move to be played, null will be returned.
     */
    Stack<Move> getValidatedMoves(){
        if(jumpMoveExists)
            return null;
        Stack<Move> ret = new Stack<>();
        while(!validatedMoves.empty())
            ret.push(validatedMoves.pop());
        jumpedSpaces.clear();
        return ret;
    }

    /**
     * Generates a set of valid Moves.
     * @return all possible valid Moves
     */
    private Set<Move> generateValidMoves(){
        Set<Move> validMoves = new HashSet<>(8);
        int[] adjustments = {-2, -1, 1, 2};
        jumpMoveExists = false;
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++){
                // Find all Pieces that the current player owns.
                Position currPos = new Position(row, col);
                Piece currPiece = temp.getPiece(currPos);
                if( currPiece == null || currPiece.getColor() != playerColor)
                    continue;
                // Test all possible Moves that player can make.
                for(int rowAdjust : adjustments){
                    for(int colAdjust : adjustments){
                        int currRow = currPos.getRow();
                        int currCol = currPos.getCell();
                        Position nextPos = new Position(currRow + rowAdjust, currCol + colAdjust);

                        if(temp.outOfBounds(nextPos))
                            continue;

                        Move testMove = new Move(new Move(currPos, nextPos), currPiece);
                        if(testMove(testMove).getType() == Message.Type.info) {
                            validMoves.add(testMove);
                            if(testMove.getMoveType() == Move.Type.JUMP)
                                jumpMoveExists = true;
                        }
                    }
                }
            }
        }

        // SINGLE Moves must be excluded if a Jump move is possible.
        if(jumpMoveExists){
            Set<Move> ret = new HashSet<>(8);
            for(Move move : validMoves){
                if(move.getMoveType() == Move.Type.JUMP)
                    validMoves.add(move);
            }
            validMoves = ret;
        }

        return validMoves;
    }

    /**
     * @return An unmodifiable set of possible moves
     */
    Set<Move> getPossibleMoves() {
        return Collections.unmodifiableSet(possibleMoves);
    }
}
