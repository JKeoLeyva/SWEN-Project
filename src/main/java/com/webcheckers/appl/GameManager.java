package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An application service for managing all ongoing games.
 */
public class GameManager {
    private Map<Player, Game> games;

    public GameManager(Map<Player, Game> map) {
        games = map;
    }

    /**
     * Returns true if both input players are available.
     *
     * @param player1 to-be red player
     * @param player2 to-be white player
     * @return if board add was successful
     */
    public synchronized boolean createGame(Player player1, Player player2) {
        // returns if both players are available
        if(!canCreateGame(player1, player2))
            return false;

        Game game = new Game(player1, player2);
        games.put(player1, game);
        games.put(player2, game);
        return true;
    }

    /**
     * Check if a board can be created
     *
     * @param p1 one of the players to be used
     * @param p2 the other player to be used
     * @return if a board can be created with these players
     */
    public synchronized boolean canCreateGame(Player p1, Player p2) {
        if(p1 == null || p2 == null)
            return false;
        return !games.containsKey(p1) && !games.containsKey(p2) && !p1.equals(p2);
    }

    /**
     * @param player that may be in a game
     * @return the game that player is in, if any
     */
    public synchronized Game getGame(Player player) {
        return games.get(player);
    }

    public synchronized void deleteGame(Player player) {
        Game game = games.get(player);
        game.setGameOver();
        games.remove(player);
    }
}
