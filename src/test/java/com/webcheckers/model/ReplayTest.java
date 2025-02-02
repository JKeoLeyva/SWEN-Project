package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Tag("Model-tier")
public class ReplayTest {
    @Test
    void newReplay() {
        // Create replay
        Player redPlayer = new Player("Dank");
        Player whitePlayer = new Player("Memes");
        Queue<Move> moves = new LinkedList<>();
        moves.add(new Move(new Position(1,1), new Position(2,2)));
        Game game = mock(Game.class);
        when(game.getSubmittedMoves()).thenReturn(moves);
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);

        Replay replay = new Replay(game);

        // Verify the moves were retrieved and stored correctly
        assertEquals(moves, replay.getMoves());
        assertEquals(replay.getName(game.getRedPlayer()), "You vs. Memes");
        assertEquals(replay.getName(game.getWhitePlayer()), "You vs. Dank");
        assertEquals(replay.getRedPlayer(), redPlayer);
        assertEquals(replay.getWhitePlayer(), whitePlayer);

        //noinspection ResultOfMethodCallIgnored
        verify(game).getSubmittedMoves();
    }
}
