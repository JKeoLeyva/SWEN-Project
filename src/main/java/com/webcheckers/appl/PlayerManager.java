package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Player;

import java.util.*;

/**
 * An application service for managing all ongoing games,
 * and all signed-in players.
 */

public class PlayerManager {
    private Set<Player> players;
    private Map<Player, Board> games;

    public PlayerManager() {
        players = new HashSet<>();
        games = new HashMap<>();
    }

    /**
     * Returns true if both input players are available.
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
     * @param name that a user wants to log in with
     * @return true if the name is available
     */
    public synchronized boolean isNameAvailable(String name) {
        return !players.contains(new Player(name));
    }

    public synchronized Player signInPlayer(String name) {
        if(!isNameAvailable(name))
            return null;

        Player player = new Player(name);
        players.add(player);
        return player;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public int getPlayerCount() {
        return players.size();
    }

    /**
     * @param player that may be in a game
     * @return what game that player is in
     */
    public Board getBoard(Player player) {
        if(player == null) return null;
        return games.get(player);
    }
}
