package com.webcheckers.appl;

import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Application-tier")
public class GameManagerTest {
    private GameManager gameManager;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void setup() {
        gameManager = new GameManager();
        player1 = new Player("player1");
        player2 = new Player("player2");
        player3 = new Player("player3");
    }

    @Test
    public void newBoard() {
        assertTrue(gameManager.canCreateBoard(player1, player2));
        assertTrue(gameManager.addBoard(player1, player2));

        Map<Player, Board> boards = gameManager.getBoards();

        assertEquals(2, boards.size());
        assertTrue(boards.containsKey(player1));
        assertTrue(boards.containsKey(player2));

        Board board = gameManager.getBoard(player1);

        assertEquals(board, gameManager.getBoard(player2));
        assertEquals(board, boards.get(player1));
        assertEquals(board, boards.get(player2));
    }

    @Test
    public void playerAlreadyInGame() {
        gameManager.addBoard(player2, player3);

        assertFalse(gameManager.canCreateBoard(player1, player3));
        assertFalse(gameManager.addBoard(player1, player3));

        Map<Player, Board> boards = gameManager.getBoards();

        assertEquals(2, boards.size());
        assertTrue(boards.containsKey(player2));
        assertTrue(boards.containsKey(player3));
    }

    @Test
    public void noOnePlayerGame() {
        assertFalse(gameManager.canCreateBoard(player1, player1));
        assertFalse(gameManager.addBoard(player1, player1));
    }

    @Test
    public void getNullPlayerBoard() {
        assertNull(gameManager.getBoard(null));
    }
}
