package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("Application-tier")
public class ReplayManagerTest {

    private ReplayManager replayManager;
    private Game game;
    private static final Player player1 = new Player("player1");
    private static final Player player2 = new Player("player2");

    @BeforeEach
    void setUp() {
        this.game = new Game(player1, player2);
        this.replayManager = new ReplayManager();
    }

    @Test
    void addReplay() {
        game.tryMove(new Move(new Position(5, 0), new Position(4, 1)));
        game.submitTurn();

        replayManager.addReplay(game, player1);

        assertNotNull(replayManager.getReplay(player1));
    }

    @Test
    void deleteReplay() {
        addReplay();

        replayManager.deleteReplay(player1);
        assertNull(replayManager.getReplay(player1));
    }
}
