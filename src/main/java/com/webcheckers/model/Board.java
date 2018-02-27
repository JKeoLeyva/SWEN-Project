package com.webcheckers.model;

import com.webcheckers.ui.Piece;

/**
 * @author Jacob Keegan
 * Sets up and stores a checkers board.
 */
public class Board {
    public final static int BOARD_SIZE = 8;
    private Piece[][] board;

    public Board(){
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        setUpPlayer(Piece.Color.WHITE);
        for(int row = 3; row < 5; row++){
            for(int col = 0; col < 8; col++){
                board[row][col] = null;
            }
        }
        setUpPlayer(Piece.Color.RED);
    }

    // Sets up either player.
    private void setUpPlayer(Piece.Color color){
        // Determines where to start placing pieces depending on player.
        int initRow = (color == Piece.Color.WHITE ? 0 : 5);
        for(int row = initRow; row < initRow + 3; row++){
            for(int col = 0; col < 8; col++){
                // Determines if a piece or no piece should be placed.
                Piece newPiece = row%2 != col%2 ?
                        new Piece(Piece.Type.SINGLE, color) : null;
                board[row][col] = newPiece;
            }
        }
    }

    // Returns what Piece is at the given location.
    public Piece getPiece(int row, int col){
        return board[row][col];
    }

    // Sets the Piece at the given location to the given piece.
    public void setPiece(int row, int col, Piece piece){
        board[row][col] = piece;
    }

}
