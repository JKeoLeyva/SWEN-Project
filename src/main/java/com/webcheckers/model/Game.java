package com.webcheckers.model;

import com.webcheckers.appl.Message;
import com.webcheckers.ui.BoardView;

import java.util.*;

import static com.webcheckers.model.Board.BOARD_SIZE;

/**
 * This is used to represent a Game of Checkers between two players.
 */
public class Game {
    private Board board;
    private Player redPlayer;
    private Player whitePlayer;
    private State currState;
    private Turn turn;
    // Stores all moves made during the game.
    private Queue<Move> submittedMoves = new LinkedList<>();

    static final String NO_MOVES = "No moves were made.";
    static final String MOVE_FORCED = "There is still a forced jump move to make.";
    static final String TURN_SUBMITTED = "Turn submitted successfully!";

    private enum State {
        WAITING_FOR_RED,
        WAITING_FOR_WHITE,
        GAME_OVER
    }


    public Game(Player redPlayer, Player whitePlayer) {
        this(redPlayer, whitePlayer, new Board());
    }

    // For creating a Game out of a pre-made board.
    Game(Player redPlayer, Player whitePlayer, Board board){
        this.board = new Board(board);
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.currState = State.WAITING_FOR_RED;
        clearTurn();
    }

    /**
     * retrieves the red player from the game
     * @return the red player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * retrieves the white player from the game
     * @return the white player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Used to see what the current state of the game is
     * @return the color of the current active piece, red or white
     */
    public Piece.Color getActiveColor() {
        return currState == State.WAITING_FOR_RED ? Piece.Color.RED : Piece.Color.WHITE;
    }

    /**
     * Used to set the BoardView for the white player
     * @param player used to get the BoardView for the white player
     * @return The BoardView for the white player
     */
    public BoardView makeBoardView(Player player) {
        return new BoardView(board, whitePlayer.equals(player));
    }

    /**
     * Checks to see if the game is over
     * @return a boolean, true if one of the players is unable to make a move
     */
    public boolean isGameOver() {
        return currState == State.GAME_OVER || noMove(redPlayer) || noMove(whitePlayer);
    }


    /**
     * Checks if a given player can still play.
     * @param player to be tested
     * @return if player can make a move
     */
    private boolean noMove(Player player){
        Piece.Color color = player.equals(redPlayer) ? Piece.Color.RED : Piece.Color.WHITE;
        Turn testTurn = new Turn(board, color, new Stack<>(), new HashSet<>());
        return testTurn.getPossibleMoves().size() == 0;
    }

    public void setGameOver(){
        currState = State.GAME_OVER;
    }

    /**
     * Checks if it is the input player's turn.
     * @param player to check
     * @return if it is player's turn
     */
    public boolean isMyTurn(Player player) {
        if(currState == State.GAME_OVER)
            // Forces a page reload.
            return true;
        if(redPlayer.equals(player)) {
            return getActiveColor() == Piece.Color.RED;
        } else if(whitePlayer.equals(player)) {
            return getActiveColor() == Piece.Color.WHITE;
        } else {
            return false;
        }
    }

    /**
     * Makes the moves validated within Turn, then switches State,
     * and creates a turn for the next player.
     * If there are jump moves still to be made, returns an error message.
     */
    public Message submitTurn() {
        // Gets the validated moves stored in Turn.
        Stack<Move> validMoves = turn.getValidatedMoves();
        if(validMoves == null)
            return new Message(MOVE_FORCED, Message.Type.error);
        // If no moves were made, the turn should not be switched.
        if(validMoves.size() == 0)
            return new Message(NO_MOVES, Message.Type.error);
        while(!validMoves.empty())
            makeMove(validMoves.pop());
        // Switches the cuurent state.
        currState = (getActiveColor() == Piece.Color.RED ?
                State.WAITING_FOR_WHITE : State.WAITING_FOR_RED);
        clearTurn();
        return new Message(TURN_SUBMITTED, Message.Type.info);
    }

    /**
     * Game's method to make a move. Changes the Board according to what the
     * move says, and removes jumped pieces.
     * @param move to be registered
     */
    private void makeMove(Move move) {
        Piece moving = board.getPiece(move.getStart());
        board.setPiece(move.getStart(), null);

        if(move.getEnd().getRow() == 0 && moving.getColor() == Piece.Color.RED)
            moving = new Piece(Piece.Type.KING, Piece.Color.RED);
        else if(move.getEnd().getRow() == BOARD_SIZE-1 && moving.getColor() == Piece.Color.WHITE)
            moving = new Piece(Piece.Type.KING, Piece.Color.WHITE);

        board.setPiece(move.getEnd(), moving);
        if(move.getMoveType() == Move.Type.JUMP) {
            Position jumped = move.getJumped();
            board.setPiece(jumped, null);
        }
        submittedMoves.add(move);
    }

    /**
     * Clears validates moves. Used whenever /game is loaded, to prevent not
     * being able to make moves if you validate a move then reload the page.
     */
    public void clearTurn(){
        turn = new Turn(board, getActiveColor(), new Stack<>(), new HashSet<>());
    }

    public void backupMove(){
        turn.backupMove();
    }

    public Message tryMove(Move move){
        return turn.tryMove(move);
    }

    Queue<Move> getSubmittedMoves() {
        return submittedMoves;
    }

    /**
     * @return An unmodifiable set of possible moves
     */
    public Set<Move> getPossibleMoves() {
        return turn.getPossibleMoves();
    }
}
