package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class MoveTest {

    /**
     * Basic checking of every case of the equals method.
     */
    @Test
    void equals() {
        Position zero = new Position(0, 0);
        Position rowIsOne = new Position(1, 0);
        Position colIsOne = new Position(0, 1);
        Position one = new Position(1, 1);

        Move move = new Move(zero, zero);
        assertEquals(move, move);
        assertNotEquals(move, null);
        assertNotEquals(move, "");
        Move move2 = new Move(zero, zero);
        assertEquals(move, move2);
        move2 = new Move(zero, one);
        assertNotEquals(move, move2);
        move2 = new Move(rowIsOne, zero);
        assertNotEquals(move, move2);
        move2 = new Move(rowIsOne, colIsOne);
        assertNotEquals(move, move2);
    }

}