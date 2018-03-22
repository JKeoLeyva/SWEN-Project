package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.*;

/**
 * A class for listing all players.
 */
public class PlayerLobby {
    private Set<Player> players;
    private Set<Player> onlinePlayers;

    public PlayerLobby() {
        players = new HashSet<>();
        onlinePlayers = new HashSet<>();
    }

    /**
     * @param name that a user wants to log in with
     * @return true if the name is available
     */
    public boolean isNameAvailable(String name) {
        return !players.contains(new Player(name));
    }

    /**
     * Attempt to sign in a new player
     *
     * @param name the player's name
     * @return the new Player, if the name is available
     */
    public Player signInPlayer(String name) {
        if(!isNameAvailable(name))
            return null;

        Player player = new Player(name);
        players.add(player);
        onlinePlayers.add(player);
        return player;
    }

    /**
     * Attempt to sign out a player
     *
     * @param name the player's name
     * @return if the player could be signed out
     */
    public boolean signOutPlayer(String name) {
        return onlinePlayers.remove(new Player(name));
    }

    /**
     * Check if a player is online
     *
     * @param name the player's name
     * @return if the player is online
     */
    public boolean isPlayerOnline(String name) {
        return onlinePlayers.contains(new Player(name));
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(new ArrayList<>(players));
    }

    public int getPlayerCount() {
        return players.size();
    }
}
