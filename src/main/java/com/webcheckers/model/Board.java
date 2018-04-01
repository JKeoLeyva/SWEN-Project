package com.webcheckers.model;

/**
 * Sets up and stores a single checkers board.
 */
public class Board {
    public final static int BOARD_SIZE = 8;
    private Piece[][] board;

    /**
     * Create a new board with the specified players
     */
    public Board() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        setUpPlayer(Piece.Color.WHITE);

        for(int row = 3; row < 5; row++) {
            for(int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = null;
            }
        }

        setUpPlayer(Piece.Color.RED);
    }

    /**
     * Sets up a player of either color.
     *
     * @param color of player to be set up
     */
    private void setUpPlayer(Piece.Color color) {
        // Determines where to start placing pieces depending on player.
        int initRow = (color == Piece.Color.WHITE ? 0 : 5);

        for(int row = initRow; row < initRow + 3; row++) {
            for(int col = 0; col < BOARD_SIZE; col++) {
                // Determines if a piece or no piece should be placed.
                Piece newPiece = row % 2 != col % 2 ?
                        new Piece(Piece.Type.SINGLE, color) : null;
                board[row][col] = newPiece;
            }
        }
    }

    /**
     * @return the Piece at the given location.
     */
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    /**
     * Set the Piece at the given location to the given piece.
     */
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

}
