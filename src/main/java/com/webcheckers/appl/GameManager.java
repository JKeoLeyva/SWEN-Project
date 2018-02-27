package com.webcheckers.appl;

import com.webcheckers.model.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// @author Karl Coelho
// An application service for managing the multiple games ongoing.

public class GameManager {
    private Map<String, Board> games;

    public GameManager() {
    }

    public void createBoard(String name, Board board) {
        this.games.put(name, board);
    }

    public Map<String, Board> getBoards() {
        return games;
    }

    public Board getBoard(String name) {
        return games.get(name);
    }
}
