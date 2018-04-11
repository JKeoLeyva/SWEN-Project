package com.webcheckers.model;

import java.util.LinkedList;
import java.util.Queue;

public class Replay {
    private Game game;
    private Queue<Move> moves;

    public Replay(Game game) {
        this.game = game;
        this.moves = new LinkedList<Move>();
    }

    public void addMove(Move move) {
        moves.add(move);
    }
}
