package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Player;

import java.util.HashMap;
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

    public synchronized boolean canCreateBoard(Player p1, Player p2) {
        return !games.containsKey(p1) && !games.containsKey(p2);
    }

    public Map<Player, Board> getBoards() {
        return games;
    }

    public Board getBoard(Player player) {
        if(player == null) return null;
        return games.get(player);
    }
}
