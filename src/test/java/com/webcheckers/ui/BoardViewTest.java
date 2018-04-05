package com.webcheckers.ui;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UI-tier")
class BoardViewTest {

    private static Board board;
    private static BoardView CuT;
    private static Iterator<Row> rowIterator;

    @BeforeAll
    static void create() {
        board = new Board();
    }

    @Test
    void noBoard(){
        assertThrows(NullPointerException.class, () ->
        {new BoardView(null, true);},
                "Contructed allowed a non-existent board.");
    }

    // Test the BoardView forward.
    @Test
    void iteraorForward(){
        CuT = new BoardView(board, false);
        rowIterator = CuT.iterator();
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            Row currRow = rowIterator.next();
            assertEquals(currRow.getIndex(), row);
        }
        assertFalse(rowIterator.hasNext());
    }

    // Test the BoardView reversed.
    @Test
    void iteratorReverse(){
        CuT = new BoardView(board, true);
        rowIterator = CuT.iterator();
        for(int row = Board.BOARD_SIZE-1; row >= 0; row--){
            Row currRow = rowIterator.next();
            assertEquals(currRow.getIndex(), row);
        }
        assertFalse(rowIterator.hasNext());
    }
}