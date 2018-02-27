package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.ui.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @author Karl Coelho
// An application service for managing the multiple games ongoing.

public class GameManager {
    private Map<String, Board> games;

    public GameManager() {
        games = new HashMap<>();
    }

    public synchronized boolean createBoard(String name, Board board) {
        if(games.containsKey(name)) return false;
        games.put(name, board);
        return true;
    }

    public Map<String, Board> getBoards() {
        return games;
    }

    public Board getBoard(Player player) {
        if(player == null) return null;
        return games.get(player.getName());
    }
}
