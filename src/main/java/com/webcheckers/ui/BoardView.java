package com.webcheckers.ui;

import com.webcheckers.model.Board;
import com.webcheckers.model.Row;
import com.webcheckers.model.Space;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A class that displays a checkers board.
 */
public class BoardView implements Iterable<Row> {
    private List<Row> rows;

    /**
     * Generates a view of an input board.
     *
     * @param gameBoard the board to be viewed
     * @param reversed  if white should be on bottom
     */
    public BoardView(Board gameBoard, boolean reversed) {
        rows = new ArrayList<>(Board.BOARD_SIZE);
        for(int row = 0; row < Board.BOARD_SIZE; row++) {
            List<Space> spaces = new ArrayList<>(Board.BOARD_SIZE);

            // Creates an array of 8 spaces, containing their proper pieces.
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                spaces.add(new Space(row, col, gameBoard.getPiece(row, col)));
            }

            if(reversed)
                Collections.reverse(spaces);

            // Assigns these spaces to a single row.
            rows.add(new Row(row, spaces));
        }

        if(reversed)
            Collections.reverse(rows);
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

}
