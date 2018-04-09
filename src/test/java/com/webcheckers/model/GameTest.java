package com.webcheckers.model;

import com.webcheckers.appl.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class GameTest {
    private static final Player player1 = new Player("player1");
    private static final Player player2 = new Player("player2");
    private static final Piece WHITE_SINGLE = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
    private static final Piece RED_SINGLE = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
    private static final Piece WHITE_KING = new Piece(Piece.Type.KING, Piece.Color.WHITE);
    private static final Piece RED_KING = new Piece(Piece.Type.KING, Piece.Color.RED);
    private Game game;
    private Board emptyBoard;

    @BeforeEach
    void setup() {
        game = new Game(player1, player2);
        emptyBoard = new Board();
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++){
                emptyBoard.setPiece(new Position(row, col), null);
            }
        }
    }

    /**
     * Tests that the players were set up correctly.
     */
    @Test
    void testGetPlayers() {
        assertEquals(game.getActiveColor(), Piece.Color.RED);
        assertEquals(game.getRedPlayer(), player1);
        assertEquals(game.getWhitePlayer(), player2);
    }

    /**
     * Tests that setting the game to be over works.
     */
    @Test
    void testSetGameOver(){
        game.setGameOver();
        assertTrue(game.isGameOver());
        assertTrue(game.isGameOver());
        assertThrows(IllegalStateException.class, () -> game.getActiveColor());
    }

    /**
     * Tests if a player has won for both white and red players.
     */
    @Test
    void testAPlayerWon(){
        // An empty board is impossible, but it would still mean a game over state.
        game = new Game(player1, player2, emptyBoard);
        assertTrue(game.isGameOver());
        // Tests if the game ends when one white piece remains.
        Board curr = new Board(emptyBoard);
        curr.setPiece(new Position(0, 1), WHITE_SINGLE);
        game = new Game(player1, player2, curr);
        assertTrue(game.isGameOver());
        // Tests if the game ends when one red piece remains.
        curr = new Board(emptyBoard);
        curr.setPiece(new Position(0, 1), RED_KING);
        game = new Game(player1, player2, curr);
        assertTrue(game.isGameOver());
        // Tests that the game continues if there are pieces of both colors.
        curr.setPiece(new Position(0, 3), WHITE_KING);
        game = new Game(player1, player2, curr);
        assertFalse(game.isGameOver());
    }

    /**
     * Tests game's hasMove method indirectly.
     */
    @Test
    void testHasMove(){
        Board curr = new Board(emptyBoard);
        curr.setPiece(new Position(2, 1), WHITE_SINGLE);
        curr.setPiece(new Position(3, 0), RED_SINGLE);
        curr.setPiece(new Position(3, 2), RED_SINGLE);
        curr.setPiece(new Position(4, 3), RED_SINGLE);
        Game currGame = new Game(player1, player2, curr);
        assertTrue(currGame.isGameOver());
    }

    /**
     * Tests the method that returns if it is a player's turn.
     */
    @Test
    void testIsMyTurn(){
        assertTrue(game.isMyTurn(player1));
        assertFalse(game.isMyTurn(player2));

        Move valid = new Move(new Position(5, 0), new Position(4, 1));
        game.tryMove(valid);
        game.backupMove();
        // Checks that the turn is not switched if no moves were submitted.
        game.submitTurn();
        assertTrue(game.isMyTurn(player1));
        assertFalse(game.isMyTurn(player2));

        game.tryMove(valid);
        game.submitTurn();
        assertFalse(game.isMyTurn(player1));
        assertTrue(game.isMyTurn(player2));

        valid = new Move(new Position(2, 1), new Position(3, 2));
        game.tryMove(valid);
        game.submitTurn();
        assertTrue(game.isMyTurn(player1));
        assertFalse(game.isMyTurn(player2));

        // Tests that the clearTurn method works.
        valid = new Move(new Position(5, 2), new Position(4, 3));
        game.tryMove(valid);
        game.clearTurn();
        game.submitTurn();
        assertTrue(game.isMyTurn(player1));
        assertFalse(game.isMyTurn(player2));

        // If you aren't in the game, it isn't your turn.
        assertFalse(game.isMyTurn(new Player("")));

        valid = new Move(new Position(5, 6), new Position(4, 7));
        game.tryMove(valid);
        // If the game is over, method says it's your turn,
        // so the page can be reloaded.
        game.setGameOver();
        assertTrue(game.isMyTurn(player1));
        // Tests odd case for when the game is already over.
        game.submitTurn();
    }
}
