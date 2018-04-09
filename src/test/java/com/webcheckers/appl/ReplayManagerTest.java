package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Appl-tier")
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
    void addNewReplay() {
        Replay replay = new Replay(game);
        replayManager.addReplay(game, player1);
        //assertEquals(replayManager.getReplay(player1), replay);
    }
}
