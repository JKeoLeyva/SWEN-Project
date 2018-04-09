package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

public class Replay {
    private Game game;
    private List<Move> moves;

    public Replay(Game game) {
        this.game = game;
        this.moves = new ArrayList<Move>();
    }

    public void addMove(Move move) {
        moves.add(move);
    }
}
