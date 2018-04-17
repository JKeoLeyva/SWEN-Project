package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("Model-tier")
class SpaceTest {

    /**
     * Generic test for all Space configurations
     *
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
        assertEquals(new Position(row, cellIdx), space.asPosition());
    }

    /**
     * Tests the valid space w/ piece
     */
    @Test
    void testValidSpacePiece() {
        Piece p = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        testSpace(p, 1, 1, "[" + p.toString() + "]" + " (1, 1)", false);
        testSpace(p, 1, 2, "[" + p.toString() + "]" + " (1, 2)", false);
    }

    /**
     * Tests the valid space w/o piece
     */
    @Test
    void testValidSpaceNoPiece() {
        testSpace(null, 1, 2, "[ ]" + " (1, 2)", true);
    }

    /**
     * Tests invalid spaces
     */
    @Test
    void testInvalidSpaces() {
        assertThrows(IllegalArgumentException.class, () -> new Space(0, -1, null));
        assertThrows(IllegalArgumentException.class, () -> new Space(0, Board.BOARD_SIZE+1, null));
    }
}
