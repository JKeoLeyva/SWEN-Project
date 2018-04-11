package com.webcheckers.model;

import java.util.Queue;

public class Replay {
    private Game game;
    private Queue<Move> moves;

    public Replay(Game game) {
        this.game = game;
        this.moves = game.getSubmittedMoves();
    }

    public Queue<Move> getMoves() {
        return moves;
    }
}
