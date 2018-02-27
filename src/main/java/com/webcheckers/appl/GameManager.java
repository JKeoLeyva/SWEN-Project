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

    public synchronized boolean addBoard(Player player1, Player player2) {
        // returns if both players are available
        if(games.containsKey(player1) || games.containsKey(player2))
            return false;
        // creates and adds a board if both players are available
        Board board = new Board(player1, player2);
        games.put(player1, board);
        games.put(player2, board);
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
