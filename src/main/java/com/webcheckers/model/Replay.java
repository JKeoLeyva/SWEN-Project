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

    public String getName(Player player) {
        if(player.equals(game.getRedPlayer())) {
            return "You vs. " + game.getWhitePlayer().getName();
        } else {
            return "You vs. " + game.getRedPlayer().getName();
        }
    }
}
