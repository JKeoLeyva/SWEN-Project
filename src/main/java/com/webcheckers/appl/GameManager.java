package com.webcheckers.appl;

import com.webcheckers.model.Board;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private List<Board> boards;

    public GameManager() {
        this.boards = new ArrayList<>();
    }

    public void createBoard(Board board) {
        this.boards.add(board);
    }

    public List<Board> getBoards() {
        return boards;
    }
}
