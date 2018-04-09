package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class ReplayTest {
    private Replay replay;
    private Game game;
    private static final Player player1 = new Player("player1");
    private static final Player player2 = new Player("player2");

    @BeforeEach
    void setUp() {
        this.game = new Game(player1, player2);
        this.replay = new Replay(game);
    }

    @Test
    void newReplay() {
        // Replay doesnt do anything besides store the move since
        // since all I have to do is store replays and their moves
    }
}
