package com.webcheckers.model;

import com.webcheckers.appl.Message;
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
        turn = new Turn(board, Piece.Color.RED);
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

    public boolean isGameOver(Player player) {
        return hasAPlayerWon() || !hasMove(player) || isGameOver();
    }

    public boolean isGameOver(){
        return currState == State.GAME_OVER;
    }

    public void setGameOver(){
        currState = State.GAME_OVER;
    }

    public boolean hasAPlayerWon() {
        boolean redFound = false;
        boolean whiteFound = false;
        Piece piece;

        for(int i = 0; i < Board.BOARD_SIZE; i++) {
            for(int j = 0; j < Board.BOARD_SIZE; j++) {
                piece = board.getPiece(i, j);
                if(piece != null) {
                    if(piece.getColor() == Piece.Color.RED)
                        redFound = true;
                    if(piece.getColor() == Piece.Color.WHITE)
                        whiteFound = true;
                }
            }
        }

        return !(redFound && whiteFound);
    }

    public boolean isMyTurn(Player player) {
        if(currState == State.GAME_OVER)
            return true;
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
            makeMove(move, getActiveColor());

        if(currState == State.WAITING_FOR_RED) {
            currState = State.WAITING_FOR_WHITE;
            turn = new Turn(board, Piece.Color.WHITE);
        } else {
            currState = State.WAITING_FOR_RED;
            turn = new Turn(board, Piece.Color.RED);
        }
    }

    public boolean hasMove(Player player){
        Piece.Color color = player.equals(redPlayer) ? Piece.Color.RED : Piece.Color.WHITE;
        Piece piece;
        Position start, end;
        int rowAdjustment = color == Piece.Color.RED ? -1 : 1;
        Turn turn = new Turn(board, color);

        for(int row = 0; row < Board.BOARD_SIZE; row++) {
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                piece = board.getPiece(row, col);
                if(piece != null && piece.getColor() == color) {
                    start = new Position(row, col);

                    end = new Position(row + rowAdjustment, col - 1);
                    if(turn.tryMove(new Move(start, end)).getType() == Message.Type.info)
                        return true;

                    end = new Position(row + rowAdjustment, col + 1);
                    if(turn.tryMove(new Move(start, end)).getType() == Message.Type.info)
                        return true;

                    end = new Position(row + rowAdjustment * 2, col - 2);
                    if(turn.tryMove(new Move(start, end)).getType() == Message.Type.info)
                        return true;

                    end = new Position(row + rowAdjustment * 2, col + 2);
                    if(turn.tryMove(new Move(start, end)).getType() == Message.Type.info)
                        return true;
                }
            }
        }

        return false;
    }

    public void makeMove(Move move, Piece.Color playerColor) {
        board.makeMove(move, playerColor);
        submittedMoves.add(move);
    }

    public Turn getTurn(){
        return turn;
    }

}
