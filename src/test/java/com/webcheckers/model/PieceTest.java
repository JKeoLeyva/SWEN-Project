package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Model-tier")
public class PieceTest {
    private void testPiece(Piece.Type type, Piece.Color color, String toString) {
        // Create the piece
        Piece piece = new Piece(type, color);

        // Verify the piece
        assertEquals(color, piece.getColor());
        assertEquals(type, piece.getType());
        assertEquals(toString, piece.toString());
    }

    @Test
    public void testSingleRed() {
        testPiece(Piece.Type.SINGLE, Piece.Color.RED, "Red Single");
    }

    @Test
    public void testSingleWhite() {
        testPiece(Piece.Type.SINGLE, Piece.Color.WHITE, "White Single");
    }

    @Test
    public void testKingRed() {
        testPiece(Piece.Type.KING, Piece.Color.RED, "Red King");
    }

    @Test
    public void testKingWhite() {
        testPiece(Piece.Type.KING, Piece.Color.WHITE, "White King");
    }
}
