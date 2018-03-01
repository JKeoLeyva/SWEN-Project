package com.webcheckers.model;

import java.util.Iterator;
import java.util.List;

/**
 * A class to represent a single checkers board row.
 */
public class Row implements Iterable<Space> {

    private List<Space> spaces;
    private int index;

    public Row(int index, List<Space> spaces) {
        if(spaces.size() != Board.BOARD_SIZE) {
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
