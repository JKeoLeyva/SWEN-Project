package com.webcheckers.model;

import java.util.List;

public class Replay {
    private Game game;
    private List<Move> moves;

    public Replay(Game game) {
        this.game = game;
    }

    public void storeMove(Move move) {
        moves.add(move);
    }
}
