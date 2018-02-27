package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable<Row> {

    private List<Row> rows;

    public BoardView(){
        rows = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            rows.add(new Row(i));
        }
    }

    public void populateBoard(){
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(row % 2 == col % 2) {
                    if(row < 3) {
                        rows.get(row).getSpace(col).setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.RED));
                    }
                    else if(row > 4){
                        rows.get(row).getSpace(col).setPiece(new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
                    }
                }
            }
        }
    }

    public Space getSpace(int row, int cellIdx){
        return rows.get(row).getSpace(cellIdx);
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
