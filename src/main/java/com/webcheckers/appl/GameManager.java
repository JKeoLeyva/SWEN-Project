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
    private Map<Player, Board> games;

    public GameManager() {
        games = new HashMap<>();
    }

    public synchronized boolean createBoard(Player player, Board board) {
        if(games.containsKey(player)) return false;
        games.put(player, board);
        return true;
    }

    public Map<Player, Board> getBoards() {
        return games;
    }

    public Board getBoard(Player player) {
        if(player == null) return null;
        return games.get(player);
    }
}
