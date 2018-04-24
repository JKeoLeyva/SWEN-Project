package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.*;

/**
 * Keep track of all players
 */
public class PlayerLobby {
    private Set<Player> players;

    public PlayerLobby(Set<Player> set) {
        players = set;
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
     * @param player the player
     * @return the new Player, if the name is available
     */
    public synchronized Player signInPlayer(Player player) {
        if(!isNameAvailable(player.getName()))
            return null;

        players.add(player);
        return player;
    }

    /**
     * Attempt to sign out a player
     * @param player the player
     * @return if the player could be signed out
     */
    public boolean signOutPlayer(Player player) {
        return players.remove(player);
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
