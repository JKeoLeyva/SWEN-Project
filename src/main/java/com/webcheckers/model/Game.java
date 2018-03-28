package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private Board board;
    private Player redPlayer;
    private Player whitePlayer;
    private State currState = State.WAITING_FOR_RED;
    private Turn turn;
    // Stores all moves made during the game.
    private Queue<Move> submittedMoves = new LinkedList<>();

    enum State {
        WAITING_FOR_RED,
        WAITING_FOR_WHITE,
        GAME_OVER
    }

    public Game(Player redPlayer, Player whitePlayer) {
        this.board = new Board();
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        turn = new Turn(board);
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void removePlayer(Player player) {
        if(player.equals(redPlayer)) {
            redPlayer = null;
        } else {
            whitePlayer = null;
        }
    }

    public Piece.Color getActiveColor() throws IllegalStateException {
        switch(currState) {
            case WAITING_FOR_RED:
                return Piece.Color.RED;
            case WAITING_FOR_WHITE:
                return Piece.Color.WHITE;
            default:
                throw new IllegalStateException("The game is over");
        }
    }

    public BoardView makeBoardView(Player player) {
        return new BoardView(board, player.equals(whitePlayer));
    }

    public boolean isMyTurn(Player player) {
        if(player.equals(redPlayer)) {
            return getActiveColor() == Piece.Color.RED;
        } else if(player.equals(whitePlayer)) {
            return getActiveColor() == Piece.Color.WHITE;
        } else {
            return false;
        }
    }

    public Board getBoard() {
        return board;
    }

    public void switchTurn() {

        // Makes the validated moves stored in Turn.
        for(Move move : turn.getValidatedMoves())
            makeMove(move);

        if(currState == State.WAITING_FOR_RED) {
            currState = State.WAITING_FOR_WHITE;
        } else {
            currState = State.WAITING_FOR_RED;
        }

        turn = new Turn(board);
    }

    public void makeMove(Move move) {
        board.makeMove(move);
        submittedMoves.add(move);
    }

    public Turn getTurn(){
        return turn;
    }

}
