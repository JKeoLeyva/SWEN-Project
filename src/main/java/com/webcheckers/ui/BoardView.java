package com.webcheckers.ui;

import com.webcheckers.model.Board;
import com.webcheckers.model.Row;
import com.webcheckers.model.Space;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable<Row> {

    private List<Row> rows;

    public BoardView(Board gameBoard) {
        rows = new ArrayList<>(Board.BOARD_SIZE);
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            List<Space> spaces = new ArrayList<>(Board.BOARD_SIZE);
            // Creates an array of 8 spaces, containing their proper pieces.
            for(int col = 0; col < Board.BOARD_SIZE; col++){
                spaces.add(new Space(row, col, gameBoard.getPiece(row, col)));
            }
            //Assigns these spaces to a single row.
            rows.add(new Row(row, spaces));
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    @Override
    public String toString() {
        String allRows = "";
        for(int i = 0; i < 8; i++) {
            allRows += rows.get(i) + "\n";
        }

        return allRows;
    }

//    public static void main(String[] args) {
//        BoardView b = new BoardView();
//        b.populateBoard();
//        System.out.println(b);
//
//        Space s = new Space(1);
//        s.setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));
//        System.out.println(b.getSpace(0,0));
//    }
}
