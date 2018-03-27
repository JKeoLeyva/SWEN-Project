package com.webcheckers.model;

import com.webcheckers.ui.BoardView;

public class Game {
    private Board board;
    private final Player redPlayer;
    private final Player whitePlayer;
    private State currState = State.WAITING_FOR_RED;

    enum State {
        WAITING_FOR_RED,
        WAITING_FOR_WHITE,
        GAME_OVER
    }

    public Game(Player redPlayer, Player whitePlayer) {
        this.board = new Board();
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
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
        return new BoardView(board, !isMyTurn(player));
    }

    public boolean isMyTurn(Player player) {
        if(player == redPlayer) {
            return getActiveColor() == Piece.Color.RED;
        } else if(player == whitePlayer) {
            return getActiveColor() == Piece.Color.WHITE;
        } else {
            return false;
        }
    }

    public Board getBoard() {
        return board;
    }

    public void switchTurn() {
        if(currState == State.WAITING_FOR_RED) {
            currState = State.WAITING_FOR_WHITE;
        } else {
            currState = State.WAITING_FOR_RED;
        }
    }
}
