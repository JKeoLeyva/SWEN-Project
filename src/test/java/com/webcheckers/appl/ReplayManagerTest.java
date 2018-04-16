package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotNull(replayManager.getReplays(player1));
        replayManager.addReplay(game, player2);
        assertNotNull(replayManager.getReplays(player2));

        // Add second replay for player1
        replayManager.addReplay(game, player1);
        assertEquals(2, replayManager.getReplays(player1).size());
    }

    @Test
    void deleteReplay() {
        addReplay();

        replayManager.deleteReplays(player1);
        assertNull(replayManager.getReplays(player1));
    }
}
