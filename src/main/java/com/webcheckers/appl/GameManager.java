package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * An application service for managing all ongoing games.
 */
public class GameManager {
    private Map<Player, Board> games;

    public GameManager() {
        games = new HashMap<>();
    }

    /**
     * Returns true if both input players are available.
     *
     * @param player1 to-be red player
     * @param player2 to-be white player
     * @return if board add was successful
     */
    public synchronized boolean addBoard(Player player1, Player player2) {
        // returns if both players are available
        if(games.containsKey(player1) || games.containsKey(player2))
            return false;

        Board board = new Board(player1, player2);
        games.put(player1, board);
        games.put(player2, board);
        return true;
    }

    /**
     * Check if a board can be created
     *
     * @param p1 one of the players to be used
     * @param p2 the other player to be used
     * @return if a board can be created with these players
     */
    public synchronized boolean canCreateBoard(Player p1, Player p2) {
        return !games.containsKey(p1) && !games.containsKey(p2);
    }

    public Map<Player, Board> getBoards() {
        return games;
    }

    /**
     * @param player that may be in a game
     * @return the game that player is in, if any
     */
    public Board getBoard(Player player) {
        if(player == null) return null;
        return games.get(player);
    }
}
