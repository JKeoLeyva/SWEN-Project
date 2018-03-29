package com.webcheckers.model;

import com.webcheckers.appl.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Turn {
    enum State {
        UNKNOWN, SINGLE, JUMP
    }

    // Some Strings for displaying the results of trying to validate moves.
    static final String MOVE_ALREADY_MADE = "Backup to make another move.";
    static final String INVALID_DISTANCE = "You can only move forward 1 space diagonally or make any number of valid jumps.";
    static final String TAKEN_SPACE = "You can only move onto an empty space.";
    static final String VALID_MOVE = "Move is valid!";
    static final String ALREADY_JUMPING = "You can't make a single space move after jumping.";
    static final String INVALID_SPACE = "Space is outside board.";

    // A Stack to store validated moves.
    private Stack<Move> validatedMoves;
    // A temporary Board to store moves.
    private Board temp;
    // If the turn has jumps (and therefore can't have any more single moves)
    private State state = State.UNKNOWN;
    private Piece.Color playerColor;

    public Turn(Board board, Piece.Color playerColor) {
        this.playerColor = playerColor;
        temp = new Board();
        // Copies from the original board to the temporary board.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++){
                Piece curr = board.getPiece(row, col);
                temp.setPiece(row, col, curr);
            }
        }
        validatedMoves = new Stack<>();
    }

    /**
     * Determines if a move is valid, and returns the proper message.
     * Makes the move on the temporary board if move is valid.
     * @param move to be validated
     * @return Message to be displayed
     */
    public Message tryMove(Move move) {
        int row = move.getEnd().getRow();
        int col = move.getEnd().getCell();

        if(row >= Board.BOARD_SIZE || row < 0 || col >= Board.BOARD_SIZE || col < 0)
            return new Message(INVALID_SPACE, Message.Type.error);

        boolean isJump = isJumpMove(move);

        switch(state) {
            case UNKNOWN:
                if(spaceIsTaken(move))
                    return new Message(TAKEN_SPACE, Message.Type.error);

                if(!isJump && !distanceIsValid(move))
                    return new Message(INVALID_DISTANCE, Message.Type.error);

                if(isJump)
                    state = State.JUMP;
                else
                    state = State.SINGLE;
                break;
            case SINGLE:
                return new Message(MOVE_ALREADY_MADE, Message.Type.error);
            case JUMP:
                if(distanceIsValid(move))
                    return new Message(ALREADY_JUMPING, Message.Type.error);

                if(spaceIsTaken(move))
                    return new Message(TAKEN_SPACE, Message.Type.error);

                if(!isJump)
                    return new Message(INVALID_DISTANCE, Message.Type.error);
                break;
        }

        validatedMoves.push(move);
        temp.makeMove(move);
        return new Message(VALID_MOVE, Message.Type.info);
    }

    /**
     * Helper method called by tryMove.
     * Determines if a Piece is trying to move too far.
     * @param move to be checked
     * @return if it travels too far
     */
    private boolean distanceIsValid(Move move){
        Position start = move.getStart();
        Position end = move.getEnd();

        int rowStart = start.getRow();
        int colStart = start.getCell();
        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        int verticalMove = rowEnd - rowStart;
        int horizontalMove = Math.abs(colStart - colEnd);

        // Assumes a SINGLE Piece.
        // From the perspective of a Board, red pieces can move down,
        // and white pieces can move up.
        return horizontalMove == 1 && (playerColor == Piece.Color.RED ?
                verticalMove == -1 : verticalMove == 1);
    }

    /**
     * Helper method called by tryMove.
     * Determines if a Piece is trying to move onto an occupied space.
     * @param move to be checked
     * @return if the destination is occupied
     */
    private boolean spaceIsTaken(Move move) {
        Position end = move.getEnd();

        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        Piece curr = temp.getPiece(rowEnd, colEnd);
        return (curr != null);
    }

    private boolean isJumpMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();

        // Get indices
        int rowStart = start.getRow();
        int colStart = start.getCell();
        int rowEnd = end.getRow();
        int colEnd = end.getCell();
        int verticalMove = rowEnd - rowStart;
        int horizontalMove = Math.abs(colStart - colEnd);
        int jumpRow = playerColor == Piece.Color.RED ? rowStart - 1 : rowStart + 1;
        int jumpCol = colEnd - colStart > 0 ? colStart + 1 : colStart - 1;

        // Check to make sure there's a piece of the opposite color that's being jumped
        Piece piece = temp.getPiece(jumpRow, jumpCol);
        if(piece == null || piece.getColor() == playerColor)
            return false;

        // Assumes a Jump move.
        // From the perspective of a Board, red pieces can move down,
        // and white pieces can move up.
        return horizontalMove == 2 && (playerColor == Piece.Color.RED ?
                verticalMove == -2 : verticalMove == 2);
    }

    /**
     * Reverses the last move made, and removes it from the Move stack.
     */
    public void backupMove(){
        Move toReverse = validatedMoves.pop();
        Move reversed = new Move(toReverse.getEnd(), toReverse.getStart());
        temp.makeMove(reversed);
        resetState();
    }

    /**
     * Empties out and returns the validated moves.
     * @return A Stack of moves with the first move on top, and so on.
     */
    public List<Move> getValidatedMoves(){
        List<Move> ret = new ArrayList<>();

        while(!validatedMoves.empty())
            ret.add(validatedMoves.pop());

        Collections.reverse(ret);
        resetState();

        return ret;
    }

    private void resetState() {
        switch(state) {
            case UNKNOWN:
                break;
            case JUMP:
                if(validatedMoves.empty())
                    state = State.UNKNOWN;
                break;
            case SINGLE:
                state = State.UNKNOWN;
        }
    }

    public void setPlayerColor(Piece.Color playerColor) {
        this.playerColor = playerColor;
    }
}
