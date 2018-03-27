package com.webcheckers.model;

import java.util.Iterator;
import java.util.List;

/**
 * A class to represent a single checkers board row.
 */
public class Row implements Iterable<Space> {
    private List<Space> spaces;
    private int index;

    /**
     * Create a Row from list of spaces
     *
     * @param index  the index of the row in the board
     * @param spaces the cells of the board
     * @throws IllegalArgumentException if the index is not in 0-7 and spaces.size() is not Board.BOARD_SIZE
     */
    public Row(int index, List<Space> spaces) {
        if(index < 0 || index > 8 || spaces.size() != Board.BOARD_SIZE) {
            throw new IllegalArgumentException("Index must be between 0-7");
        }

        this.index = index;
        this.spaces = spaces;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }

    // For potential testing purposes.
    @Override
    public String toString() {
        String all = "";
        for(Space s : spaces) {
            all += s;
        }
        return all;
    }
}
