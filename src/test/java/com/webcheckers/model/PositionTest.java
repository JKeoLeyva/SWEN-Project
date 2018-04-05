package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class PositionTest {

    /**
     * Basic checking of every case of the equals method.
     */
    @Test
    void equals() {
        Position zero = new Position(0, 0);
        Position rowIsOne = new Position(1, 0);
        Position colIsOne = new Position(0, 1);
        Position one = new Position(1, 1);

        assertNotEquals(zero, null);
        assertNotEquals(zero, "");
        assertEquals(zero, zero);
        assertEquals(zero, new Position(0, 0));
        assertNotEquals(zero, rowIsOne);
        assertNotEquals(zero, colIsOne);
        assertNotEquals(zero, one);
    }
}