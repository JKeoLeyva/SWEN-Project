package com.webcheckers.ui;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardViewTest {

    private Board board;
    private Piece[][] builtboard;
    private Player player1;
    private Player player2;
    private BoardView CuT;
    private Iterator<Row> rowIterator;
    private Iterator<Space> spaceIterator;

    @BeforeAll
    void setup() {
        player1 = new Player("player1");
        player2 = new Player("player2");
        board = new Board(player1, player2);
        CuT = new BoardView(board, false);
    }

    @Test
    void iterator() {
        builtboard = new Piece[Board.BOARD_SIZE][Board.BOARD_SIZE];
        rowIterator = CuT.iterator();
        Row currRow;
        Space currSpace;
        Piece currPiece;
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            currRow = rowIterator.next();
            assertTrue(currRow.getIndex() == row);
            spaceIterator = currRow.iterator();
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                currSpace = spaceIterator.next();
                assertTrue(currSpace.getCellIdx() == col);
                currPiece = currSpace.getPiece();
                assertTrue(pieceEquals(currPiece, board.getPiece(row, col)));
            }
            assertFalse(spaceIterator.hasNext());
        }
        assertFalse(rowIterator.hasNext());
    }

    private boolean pieceEquals(Piece piece1, Piece piece2){
        return piece1.getColor() == piece2.getColor() &&
                piece1.getType() == piece2.getType();
    }

}