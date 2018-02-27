package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements Iterable<Space> {

    private List<Space> spaces;
    private int index;

    public Row(int index) {
        if(index < 0 || index > 7) {
            throw new IllegalArgumentException("Index must be between 0-7");
        }

        this.index = index;

        this.spaces = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            spaces.add(new Space(index, i));
        }
    }

    public boolean spaceIsValid(int cellIdx) {
        return spaces.get(cellIdx).isValid(index);
    }

    public int getIndex() {
        return index;
    }

    public Space getSpace(int cellIdx) {
        return spaces.get(cellIdx);
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }

    @Override
    public String toString() {
        String all = "";
        for(Space s : spaces) {
            all += s;
        }
        return all;
    }
}
