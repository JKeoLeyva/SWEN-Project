package com.webcheckers.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class BoardTest {

    private static Board CuT;
    private static Piece whiteSingl = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
    private static Piece redSingl = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
    private static Piece whiteKing = new Piece(Piece.Type.KING, Piece.Color.WHITE);
    private static Piece redKing = new Piece(Piece.Type.KING, Piece.Color.RED);

    @BeforeEach
    void remakeBoard(){CuT = new Board();}

    @Test
    void getPiece() {
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    assertNull(CuT.getPiece(new Position(row, col)));
                else if(row < 3) {
                    assertEquals(CuT.getPiece(new Position(row, col)).getColor(), Piece.Color.WHITE);
                    assertEquals(CuT.getPiece(new Position(row, col)).getType(), Piece.Type.SINGLE);
                }
                else if(row > 4) {
                    assertEquals(CuT.getPiece(new Position(row, col)).getColor(), Piece.Color.RED);
                    assertEquals(CuT.getPiece(new Position(row, col)).getType(), Piece.Type.SINGLE);
                }
                else
                    assertNull(CuT.getPiece(new Position(row, col)));
            }
        }
    }

    @Test
    void setPiece() {
        // Change all pieces.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    CuT.setPiece(new Position(row, col), whiteSingl);
                else if(row < 3)
                    CuT.setPiece(new Position(row, col), redKing);
                else if(row > 4)
                    CuT.setPiece(new Position(row, col), whiteKing);
                else
                    CuT.setPiece(new Position(row, col), redSingl);
            }
        }
        // Test the changed pieces.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                if(row % 2 == col % 2)
                    assertEquals(CuT.getPiece(new Position(row, col)), whiteSingl);
                else if(row < 3)
                    assertEquals(CuT.getPiece(new Position(row, col)), redKing);
                else if(row > 4)
                    assertEquals(CuT.getPiece(new Position(row, col)), whiteKing);
                else
                    assertEquals(CuT.getPiece(new Position(row, col)), redSingl);
            }
        }
        // Clear the entire board.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                CuT.setPiece(new Position(row, col), null);
            }
        }
        // Test if the board was cleared.
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                assertNull(CuT.getPiece(new Position(row, col)));
            }
        }
    }

    /**
     * Test positions that are out of bounds.
     */
    @Test
    void outOfBounds(){
        assertTrue(CuT.outOfBounds(new Position(-1, 0)));
        assertTrue(CuT.outOfBounds(new Position(0, -1)));
        assertTrue(CuT.outOfBounds(new Position(Board.BOARD_SIZE+1, 0)));
        assertTrue(CuT.outOfBounds(new Position(0, Board.BOARD_SIZE+1)));
    }
}