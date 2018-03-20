package com.webcheckers.ui;

import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import com.webcheckers.model.Row;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Tag("UI-tier")
class BoardViewTest {

    private static Board board;
    private static Player player1;
    private static Player player2;
    private static BoardView CuT;
    private static Iterator<Row> rowIterator;

    @Test
    void iterator() {
        player1 = new Player("player1");
        player2 = new Player("player2");
        board = new Board(player1, player2);
        Row currRow;
        // Test the normal BoardView orientation.
        CuT = new BoardView(board, false);
        rowIterator = CuT.iterator();
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            currRow = rowIterator.next();
            assertEquals(currRow.getIndex(), row);
        }
        assertFalse(rowIterator.hasNext());
        // Test the BoardView reversed.
        CuT = new BoardView(board, true);
        rowIterator = CuT.iterator();
        for(int row = Board.BOARD_SIZE-1; row >= 0; row--){
            currRow = rowIterator.next();
            assertEquals(currRow.getIndex(), row);
        }
        assertFalse(rowIterator.hasNext());
    }
}