package com.webcheckers.model;

import com.webcheckers.appl.Message;
import com.webcheckers.ui.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

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
        ViewMode mode = ViewMode.PLAY;
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

    /**
     * Test kinging both a white and a red piece.
     */
    @Test
    void testKinging(){
        Position redStart = new Position(1, 0);
        Position whiteStart = new Position(6, 1);
        Position redEnd = new Position(0, 1);
        Position whiteEnd = new Position(7, 0);

        Board curr = new Board(emptyBoard);
        curr.setPiece(redStart, RED_SINGLE);
        curr.setPiece(whiteStart, WHITE_SINGLE);
        game = new Game(player1, player2, curr);

        game.tryMove(new Move(redStart, redEnd));
        game.submitTurn();
        game.tryMove(new Move(whiteStart, whiteEnd));
        game.submitTurn();
        // Checks that the red piece was kinged.
        Piece piece = getPieceFromGame(redEnd, game);
        assertNotNull(piece);
        assertSame(Piece.Type.KING, piece.getType());
        assertSame(Piece.Color.RED, piece.getColor());
        // Checks that the white piece was kinged.
        piece = getPieceFromGame(whiteEnd, game);
        assertNotNull(piece);
        assertSame(Piece.Type.KING, piece.getType());
        assertSame(Piece.Color.WHITE, piece.getColor());

        // Move Kings into the starting row for their color, and check that they sstay the right color and type.
        curr.setPiece(whiteStart, RED_KING);
        curr.setPiece(redStart, WHITE_KING);
        game = new Game(player1, player2, curr);
        game.tryMove(new Move(redStart, redEnd));
        game.submitTurn();
        game.tryMove(new Move(whiteStart, whiteEnd));
        game.submitTurn();
        // King colors should be switched compared to previous test.
        piece = getPieceFromGame(redEnd, game);
        assertNotNull(piece);
        assertSame(Piece.Type.KING, piece.getType());
        assertSame(Piece.Color.WHITE, piece.getColor());

        piece = getPieceFromGame(whiteEnd, game);
        assertNotNull(piece);
        assertSame(Piece.Type.KING, piece.getType());
        assertSame(Piece.Color.RED, piece.getColor());
    }

    /**
     * Simply tests a jump move, and checks that the correct piece was removed.
     * Checks through BoardView, th
     */
    @Test
    void testJump(){
        Position start = new Position(5, 0);
        Position middle = new Position(4,1);
        Position end = new Position(3, 2);
        Position whiteStart = new Position(2, 3);

        Board board = new Board();
        board.setPiece(middle, new Piece(Piece.Type.KING, Piece.Color.WHITE));
        game = new Game(player1, player2, board);
        game.tryMove(new Move(start, end));
        game.submitTurn();

        Piece piece = getPieceFromGame(end, game);
        assertNotNull(piece);
        assertSame(Piece.Type.SINGLE, piece.getType());
        assertSame(Piece.Color.RED, piece.getColor());

        game.tryMove(new Move(whiteStart, middle));
        game.submitTurn();

        piece = getPieceFromGame(middle, game);
        assertNotNull(piece);
        assertSame(Piece.Type.SINGLE, piece.getType());
        assertSame(Piece.Color.WHITE, piece.getColor());
    }

    /**
     * Tests a quadruple jump by a king piece.
     */
    @Test
    void quadJump(){
        // Sets up the pieces and the game.
        Board board = new Board(emptyBoard);
        Position start = new Position(0, 3);
        Position jumped1 = new Position(1, 2);
        Position jumped2 = new Position(1, 4);
        Position jumped3 = new Position(3, 2);
        Position jumped4 = new Position(3, 4);
        board.setPiece(start, RED_KING);
        board.setPiece(jumped1, WHITE_KING);
        board.setPiece(jumped2, WHITE_KING);
        board.setPiece(jumped3, WHITE_KING);
        board.setPiece(jumped4, WHITE_KING);
        game = new Game(player1, player2, board);
        // Makes the 4 moves.
        Position[] positions = {start, new Position(2, 1), new Position(4, 3), new Position(2, 5)};
        for(int move = 0; move < 4; move++)
            game.tryMove(new Move(positions[move], positions[(move+1)%4]));
        game.submitTurn();
        // Checks that the correct pieces were removed.
        assertNull(getPieceFromGame(jumped1, game));
        assertNull(getPieceFromGame(jumped2, game));
        assertNull(getPieceFromGame(jumped3, game));
        assertNull(getPieceFromGame(jumped4, game));
        // Checks that the original piece is at the proper position.
        assertSame(Piece.Color.RED, getPieceFromGame(start, game).getColor());
        assertSame(Piece.Type.KING, getPieceFromGame(start, game).getType());

        assertSame(Piece.Color.WHITE, game.getActiveColor());
    }

    /**
     * Using the BoardView constructor in Game,
     * returns the piece at a given position in Game.
     * @param pos to get the piece from
     * @param game1 to make the BoardView from
     * @return the Piece at that position
     */
    private Piece getPieceFromGame(Position pos, Game game1){
        BoardView view = game1.makeBoardView(player1);
        Iterator<Row> rows = view.iterator();
        for(int i = 0; i < pos.getRow(); i++)
            rows.next();
        Iterator<Space> spaces = rows.next().iterator();
        for(int i = 0; i < pos.getCell(); i++)
            spaces.next();
        return spaces.next().getPiece();
    }

    /**
     * Make sure to check if there are still forced moves to be made
     */
    @Test
    void oneMoreForcedMove() {
        // Setup test
        emptyBoard.setPiece(new Position(5, 0), new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        emptyBoard.setPiece(new Position(4, 1), new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        emptyBoard.setPiece(new Position(2, 1), new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        Move move = new Move(new Position(5, 0), new Position(3, 2));
        game = new Game(player1, player2, emptyBoard);

        // Run test
        Message turnMessage = game.tryMove(move);
        Message submitMessage = game.submitTurn();

        // Verify results
        assertEquals(new Message(Turn.VALID_MOVE, Message.Type.info), turnMessage);
        assertEquals(new Message(Game.MOVE_FORCED, Message.Type.error), submitMessage);
    }
}
