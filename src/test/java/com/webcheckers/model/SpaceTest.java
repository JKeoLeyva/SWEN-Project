package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Model-tier")
public class SpaceTest {

    /**
     * Generic test for all Space configurations
     *
     * @param type the piece type
     * @param row the space row
     * @param cellIdx the space cell index
     * @param toString the expected output for toString()
     */
    private void testSpace(Piece piece, int row, int cellIdx, String toString, boolean isValid) {

        Space space = new Space(row, cellIdx, piece);

        // Verify the space
        assertEquals(piece, space.getPiece());
        assertEquals(cellIdx, space.getCellIdx());
        assertEquals(row, space.getRow());
        assertEquals(toString, space.toString());
        assertEquals(isValid, space.isValid());
    }

    /**
     * Tests the valid space w/ piece
     */
    @Test
    public void testValidSpacePiece() {
        Piece p = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        testSpace(p, 1, 1, "[" + p.toString() + "]", false);
    }

    /**
     * Tests the valid space w/o piece
     */
    @Test
    public void testValidSpaceNoPiece() {
        testSpace(null, 1, 2, "[ ]", true);
    }

    /**
     * Tests the invalid space
     */
    @Test
    public void testInvalidSpace() {
        testSpace(null, 0, 0, "[ ]", false);
    }
}
