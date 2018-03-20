package com.webcheckers.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class BoardTest {

    private static Board board;
    private static Player player1;
    private static Player player2;
    private static Piece whiteSingl = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
    private static Piece redSingl = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
    private static Piece whiteKing = new Piece(Piece.Type.KING, Piece.Color.WHITE);
    private static Piece redKing = new Piece(Piece.Type.KING, Piece.Color.RED);

    @BeforeAll
    static void setup(){
        player1 = new Player("player1");
        player2 = new Player("player2");
    }

    @BeforeEach
    void remakeBoard(){board = new Board(player1, player2);}

    @Test
    void getPiece() {
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    assertTrue(board.getPiece(row, col) == null);
                else if(row < 3) {
                    assertTrue(board.getPiece(row, col).getColor() == Piece.Color.WHITE);
                    assertTrue(board.getPiece(row, col).getType() == Piece.Type.SINGLE);
                }
                else if(row > 4) {
                    assertTrue(board.getPiece(row, col).getColor() == Piece.Color.RED);
                    assertTrue(board.getPiece(row, col).getType() == Piece.Type.SINGLE);
                }
                else
                    assertTrue(board.getPiece(row, col) == null);
            }
        }
    }

    @Test
    void setPiece() {
        // Change all pieces.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    board.setPiece(row, col, whiteSingl);
                else if(row < 3)
                    board.setPiece(row, col, redKing);
                else if(row > 4)
                    board.setPiece(row, col, whiteKing);
                else
                    board.setPiece(row, col, redSingl);
            }
        }
        // Test the changed pieces.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    assertTrue(board.getPiece(row, col) == whiteSingl);
                else if(row < 3)
                    assertTrue(board.getPiece(row, col) == redKing);
                else if(row > 4)
                    assertTrue(board.getPiece(row, col) == whiteKing);
                else
                    assertTrue(board.getPiece(row, col) == redSingl);
            }
        }
        // Clear the entire board.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                board.setPiece(row, col, null);
            }
        }
        // Test if the board was cleared.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                assertTrue(board.getPiece(row, col) == null);
            }
        }
    }

    @Test
    void getRedPlayer() {
        assertTrue(board.getRedPlayer().equals(player1));
    }

    @Test
    void getWhitePlayer() {
        assertTrue(board.getWhitePlayer().equals(player2));
    }

    @Test
    void getActiveColor() {
        assertTrue(board.getActiveColor() == Piece.Color.RED);
    }

    @Test
    void changeActiveColor(){
        Piece.Color color1 = board.getActiveColor();
        board.changeActiveColor();
        Piece.Color color2 = board.getActiveColor();
        assertFalse(color1 == color2);
        board.changeActiveColor();
        color1 = board.getActiveColor();
        assertFalse(color1 == color2);
    }
}