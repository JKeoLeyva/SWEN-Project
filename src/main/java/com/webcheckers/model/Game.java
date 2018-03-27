package com.webcheckers.model;

import com.webcheckers.appl.Message;
import com.webcheckers.ui.BoardView;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Game {
    private Board board;
    private final Player redPlayer;
    private final Player whitePlayer;
    private State currState = State.WAITING_FOR_RED;
    private Stack<Move> validatedMoves = new Stack<>();
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
        while(!validatedMoves.empty())
            submittedMoves.add(validatedMoves.pop());
        if(currState == State.WAITING_FOR_RED) {
            currState = State.WAITING_FOR_WHITE;
        } else {
            currState = State.WAITING_FOR_RED;
        }
    }

    public Message isValid(Move move) {
        if( !validatedMoves.empty() && !validatedMoves.peek().isJump() )
            return new Message("Backup to make another move.", Message.Type.error);
        Message ret = move.isValid(board);
        if(ret.getType() == Message.Type.info)
            validatedMoves.push(move);

        System.out.println(validatedMoves.toString());

        return ret;
    }

    public void makeMove(Move move) {
        board.makeMove(move);
    }

    public void reverseMove(){
        Move toReverse = validatedMoves.pop();
        Move reversed = new Move(toReverse.getEnd(), toReverse.getStart());
        board.makeMove(reversed);
    }
}
