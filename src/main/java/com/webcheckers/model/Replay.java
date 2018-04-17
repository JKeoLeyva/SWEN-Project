package com.webcheckers.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is used to create a "replay" of a game that has already happened in order
 * for the user of the application to review their replays of their games
 */
public class Replay {
    private Game game;
    private Queue<Move> moves;

    /**
     * constructor for a replay, which consists of a game, and the submitted moves that were made
     * in that game
     * @param game used to create replay, as well as get the submitted moves
     */
    public Replay(Game game) {
        this.game = game;
        this.moves = game.getSubmittedMoves();
    }

    /**
     * This method is responsible for getting all of the moves from the Replay object
     * @return a queue of the submitted moves
     */
    public Queue<Move> getMoves() {
        return new LinkedList<>(moves);
    }

    /**
     * this methods returns the red player associated with the game in the Replay object
     * @return the red player
     */
    public Player getRedPlayer() {
        return game.getRedPlayer();
    }

    /**
     * this methods returns the white player associated with the game in the Replay object
     * @return the white player
     */
    public Player getWhitePlayer() {
        return game.getWhitePlayer();
    }

    /**
     * this method is used to print out a message which is the user name, vs their opponent
     * @param player used to determine who the user is, and display the message accordingly
     * @return a String in the format of " You vs. opponent's name"
     */
    public String getName(Player player) {
        if(player.equals(game.getRedPlayer())) {
            return "You vs. " + game.getWhitePlayer().getName();
        } else {
            return "You vs. " + game.getRedPlayer().getName();
        }
    }
}
