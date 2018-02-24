package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerLobby {
    private List<String> players;

    public PlayerLobby() {
        players = new ArrayList<>();
    }

    public boolean isNameAvailable(String name) {
        return players.contains(name);
    }

    public Player signInPlayer(String name) {
        if(!isNameAvailable(name))
            return null;

        players.add(name);
        return new Player(name);
    }

    public List<String> getPlayerNames() {
        return players;
    }
}
