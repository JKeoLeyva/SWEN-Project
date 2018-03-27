package com.webcheckers.model;

import com.webcheckers.appl.Message;
import com.webcheckers.ui.BoardView;
import javafx.geometry.Pos;

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
        return new BoardView(board, player.equals(whitePlayer));
    }

    public boolean isGameOver(Player player) {
        return hasAPlayerWon() || !hasMove(player);
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

        return redFound || whiteFound;
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
        if(currState == State.WAITING_FOR_RED) {
            currState = State.WAITING_FOR_WHITE;
        } else {
            currState = State.WAITING_FOR_RED;
        }
    }

    public Message isValid(Move move) {
        return move.isValid(board);
    }

    public boolean hasMove(Player player){
        Piece.Color color = player.equals(redPlayer) ? Piece.Color.RED : Piece.Color.WHITE;
        Piece piece;
        Position start, end;
        int rowAdjustment = color == Piece.Color.RED ? -1 : 1;

        for(int row = 0; row < Board.BOARD_SIZE; row++) {
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                piece = board.getPiece(row, col);
                if(piece != null && piece.getColor() == color) {
                    start = new Position(row, col);

                    end = new Position(row + rowAdjustment, col - 1);
                    if(isValid(new Move(start, end)).getType() == Message.Type.info)
                        return true;

                    end = new Position(row + rowAdjustment, col + 1);
                    if(isValid(new Move(start, end)).getType() == Message.Type.info)
                        return true;

                    end = new Position(row + rowAdjustment * 2, col - 2);
                    if(isValid(new Move(start, end)).getType() == Message.Type.info)
                        return true;

                    end = new Position(row + rowAdjustment * 2, col + 2);
                    if(isValid(new Move(start, end)).getType() == Message.Type.info)
                        return true;
                }
            }
        }

        return false;
    }

    public void makeMove(Move move) {
        board.makeMove(move);
    }
}
