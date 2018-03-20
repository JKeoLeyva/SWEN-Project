package com.webcheckers.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class BoardTest {

    private static Board CuT;
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
    void remakeBoard(){CuT = new Board(player1, player2);}

    @Test
    void getPiece() {
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    assertNull(CuT.getPiece(row, col));
                else if(row < 3) {
                    assertEquals(CuT.getPiece(row, col).getColor(), Piece.Color.WHITE);
                    assertEquals(CuT.getPiece(row, col).getType(), Piece.Type.SINGLE);
                }
                else if(row > 4) {
                    assertEquals(CuT.getPiece(row, col).getColor(), Piece.Color.RED);
                    assertEquals(CuT.getPiece(row, col).getType(), Piece.Type.SINGLE);
                }
                else
                    assertNull(CuT.getPiece(row, col));
            }
        }
    }

    @Test
    void setPiece() {
        // Change all pieces.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    CuT.setPiece(row, col, whiteSingl);
                else if(row < 3)
                    CuT.setPiece(row, col, redKing);
                else if(row > 4)
                    CuT.setPiece(row, col, whiteKing);
                else
                    CuT.setPiece(row, col, redSingl);
            }
        }
        // Test the changed pieces.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    assertEquals(CuT.getPiece(row, col), whiteSingl);
                else if(row < 3)
                    assertEquals(CuT.getPiece(row, col), redKing);
                else if(row > 4)
                    assertEquals(CuT.getPiece(row, col), whiteKing);
                else
                    assertEquals(CuT.getPiece(row, col), redSingl);
            }
        }
        // Clear the entire board.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                CuT.setPiece(row, col, null);
            }
        }
        // Test if the board was cleared.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                assertNull(CuT.getPiece(row, col));
            }
        }
    }

    @Test
    void getRedPlayer() {
        assertEquals(CuT.getRedPlayer(), player1);
    }

    @Test
    void getWhitePlayer() {
        assertEquals(CuT.getWhitePlayer(), player2);
    }

    @Test
    void getActiveColor() {
        assertEquals(CuT.getActiveColor(), Piece.Color.RED);
    }

    @Test
    void changeActiveColor(){
        Piece.Color color1 = CuT.getActiveColor();
        CuT.changeActiveColor();
        Piece.Color color2 = CuT.getActiveColor();
        assertNotEquals(color1, color2);
        CuT.changeActiveColor();
        color1 = CuT.getActiveColor();
        assertNotEquals(color1, color2);
    }
}