package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class RowTest {
    private Space space;
    private List<Space> spaces;

    @BeforeEach
    void setUp() {
        space = new Space(0, 0, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        spaces = new ArrayList<>();
        IntStream.range(0, 8).forEach(num -> spaces.add(space));
    }

    /**
     * Verify that invalid inputs are not allowed
     */
    @Test
    void invalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new Row(-1, spaces));
        assertThrows(IllegalArgumentException.class, () -> new Row(9, spaces));

        spaces.add(space);
        assertThrows(IllegalArgumentException.class, () -> new Row(0, spaces));
    }

    /**
     * Test getIndex
     */
    @Test
    void getIndex() {
        Row row = new Row(2, spaces);
        assertEquals(2, row.getIndex());
    }

    /**
     * Test iterator method
     */
    @Test
    void getIterator() {
        Row row = new Row(0, spaces);

        // Verify the iterators give the same data
        for(Iterator<Space> testIter = row.iterator(), expectedIter = spaces.iterator() ; expectedIter.hasNext() ; ) {
            assertTrue(testIter.hasNext());
            assertEquals(expectedIter.next(), testIter.next());
        }
    }

    /**
     * Test toString
     */
    @Test
    void display() {
        Row row = new Row(0, spaces);
        String rowStr = row.toString();
        String expectedStr = spaces.stream().map(Space::toString).reduce("", String::concat);

        assertEquals(expectedStr, rowStr);
    }
}
