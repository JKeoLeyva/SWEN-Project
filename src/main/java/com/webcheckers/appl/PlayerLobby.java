package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.*;

/**
 * Keep track of all players
 */
public class PlayerLobby {
    private Set<Player> players;

    public PlayerLobby() {
        players = new HashSet<>();
    }

    /**
     * @param name that a user wants to log in with
     * @return true if the name is available
     */
    public synchronized boolean isNameAvailable(String name) {
        return !players.contains(new Player(name));
    }

    /**
     * Attempt to sign in a new player
     *
     * @param name the player's name
     * @return the new Player, if the name is available
     */
    public synchronized Player signInPlayer(String name) {
        if(!isNameAvailable(name))
            return null;

        Player player = new Player(name);
        players.add(player);
        return player;
    }

    /**
     * Attempt to sign out a player
     *
     * @param name the player's name
     * @return if the player could be signed out
     */
    public boolean signOutPlayer(String name) {
        return players.remove(new Player(name));
    }

    /**
     * @return an immutable list of players
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(new ArrayList<>(players));
    }

    /**
     * @return the number of players
     */
    public int getPlayerCount() {
        return players.size();
    }
}
