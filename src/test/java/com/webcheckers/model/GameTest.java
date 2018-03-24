package com.webcheckers.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private static final Player player1 = new Player("player1");
    private static final Player player2 = new Player("player2");
    private Game game;

    @BeforeEach
    void setup() {
        game = new Game(player1, player2);
    }

    @Test
    void getRedPlayer() {
        assertEquals(game.getRedPlayer(), player1);
    }

    @Test
    void getWhitePlayer() {
        assertEquals(game.getWhitePlayer(), player2);
    }

    @Test
    void gameStartsOnRed() {
        assertEquals(game.getActiveColor(), Piece.Color.RED);
    }
}
