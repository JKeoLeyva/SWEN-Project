package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Model-tier")
class PieceTest {
    /**
     * Generic test for all Piece configurations
     *
     * @param type the piece type
     * @param color the piece color
     * @param toString the expected output for toString()
     */
    private void testPiece(Piece.Type type, Piece.Color color, String toString) {
        // Create the piece
        Piece piece = new Piece(type, color);

        // Verify the piece
        assertEquals(color, piece.getColor());
        assertEquals(type, piece.getType());
        assertEquals(toString, piece.toString());
    }

    /**
     * Test the single red piece
     */
    @Test
    void testSingleRed() {
        testPiece(Piece.Type.SINGLE, Piece.Color.RED, "Red Single");
    }

    /**
     * Test the single white piece
     */
    @Test
    void testSingleWhite() {
        testPiece(Piece.Type.SINGLE, Piece.Color.WHITE, "White Single");
    }

    /**
     * Test the king red piece
     */
    @Test
    void testKingRed() {
        testPiece(Piece.Type.KING, Piece.Color.RED, "Red King");
    }

    /**
     * Test the king white piece
     */
    @Test
    void testKingWhite() {
        testPiece(Piece.Type.KING, Piece.Color.WHITE, "White King");
    }
}
