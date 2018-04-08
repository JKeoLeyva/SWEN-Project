package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Application-tier")
class GameManagerTest {
    private GameManager gameManager;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setup() {
        gameManager = new GameManager();
        player1 = new Player("player1");
        player2 = new Player("player2");
        player3 = new Player("player3");
    }

    @Test
    void newBoard() {
        assertTrue(gameManager.canCreateGame(player1, player2));
        assertTrue(gameManager.createGame(player1, player2));

        Map<Player, Game> games = gameManager.getGames();

        assertEquals(2, games.size());
        assertTrue(games.containsKey(player1));
        assertTrue(games.containsKey(player2));

        Game game = gameManager.getGame(player1);

        assertEquals(game, gameManager.getGame(player2));
        assertEquals(game, games.get(player1));
        assertEquals(game, games.get(player2));
    }

    @Test
    void playerAlreadyInGame() {
        gameManager.createGame(player2, player3);

        assertFalse(gameManager.canCreateGame(player1, player3));
        assertFalse(gameManager.createGame(player1, player3));
        assertFalse(gameManager.canCreateGame(player1, player2));
        assertFalse(gameManager.createGame(player1, player2));

        Map<Player, Game> games = gameManager.getGames();

        assertEquals(2, games.size());
        assertTrue(games.containsKey(player2));
        assertTrue(games.containsKey(player3));
    }

    @Test
    void noOnePlayerGame() {
        assertFalse(gameManager.canCreateGame(player1, player1));
        assertFalse(gameManager.createGame(player1, player1));
        gameManager.createGame(player1, player2);
        assertFalse(gameManager.canCreateGame(player1, player1));
        assertFalse(gameManager.createGame(player1, player1));
    }

    @Test
    void nullPlayers(){
        assertFalse(gameManager.canCreateGame(null, player1));
        assertFalse(gameManager.canCreateGame(player1, null));
        assertFalse(gameManager.canCreateGame(null, null));
    }

    @Test
    void getNullPlayerBoard() {
        assertNull(gameManager.getGame(null));
    }

    @Test
    void deletedBoard(){
        Player player4 = new Player("player4");
        Player player5 = new Player("player5");

        gameManager.createGame(player4, player5);

        assertNotNull(gameManager.getGame(player4));
        assertNotNull(gameManager.getGame(player5));

        gameManager.deleteGame(player4);
        assertNotNull(gameManager.getGame(player5));
        assertNull(gameManager.getGame(player4));

        gameManager.deleteGame(player5);
        assertNull(gameManager.getGame(player4));
        assertNull(gameManager.getGame(player5));

        assertTrue(gameManager.canCreateGame(player4, player5));
        assertTrue(gameManager.canCreateGame(player5, player4));
    }
}
