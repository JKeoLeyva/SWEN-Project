package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerLobby {
    private List<Player> players;

    public PlayerLobby() {
        players = new ArrayList<>();
    }

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
}
