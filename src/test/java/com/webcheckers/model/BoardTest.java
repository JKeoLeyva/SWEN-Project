package com.webcheckers.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class BoardTest {

    private Board board;
    private Player player1;
    private Player player2;
    private Piece whiteSingl = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
    private Piece redSingl = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
    private Piece whiteKing = new Piece(Piece.Type.KING, Piece.Color.WHITE);
    private Piece redKing = new Piece(Piece.Type.KING, Piece.Color.RED);

    @BeforeAll
    void setup(){
        player1 = new Player("player1");
        player2 = new Player("player2");
        board = new Board(player1, player2);
    }

    @Test
    void getPiece() {
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    assertTrue(board.getPiece(row, col) == null);
                if(row < 3) {
                    assertTrue(board.getPiece(row, col).getColor() == Piece.Color.WHITE);
                    assertTrue(board.getPiece(row, col).getType() == Piece.Type.SINGLE);
                }
                if(row > 4) {
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
                if(row < 3)
                    board.setPiece(row, col, redKing);
                if(row > 4)
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
                if(row < 3)
                    assertTrue(board.getPiece(row, col) == redKing);
                if(row > 4)
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
        assertTrue(board.getRedPlayer().equals(player2));
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