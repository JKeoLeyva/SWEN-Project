package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpaceTest {

    /**
     * Generic test for all Piece configurations
     *
     * @param type the piece type
     * @param color the piece color
     * @param toString the expected output for toString()
     */
    private void testSpace(Piece.Type type, Piece.Color color, String toString) {
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
    public void testSingleRed() {

    }

}
