package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for listing all logged-in players.
 */

public class PlayerLobby {
    private List<Player> players;

    public PlayerLobby() {
        players = new ArrayList<>();
    }

    /**
     * @param name that a user wants to log in with
     * @return true if the name is available
     */
    public boolean isNameAvailable(String name) {
        return !players.contains(new Player(name));
    }

    public Player signInPlayer(String name) {
        if(!isNameAvailable(name))
            return null;

        Player player = new Player(name);
        players.add(player);
        return player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }
}
