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
     * Copy constructor.
     * @param origBoard Board to be copied
     */
    public Board(Board origBoard) {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];

        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++){
                Piece curr = origBoard.getPiece(new Position(row, col));
                setPiece(new Position(row, col), curr);
            }
        }
    }

    /**
     * Sets up a player of either color.
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
    public Piece getPiece(Position pos) {
        return board[pos.getRow()][pos.getCell()];
    }

    /**
     * Set the Piece at the given location to the given piece.
     */
    void setPiece(Position pos, Piece piece) {
        board[pos.getRow()][pos.getCell()] = piece;
    }

    /**
     * Returns true if the positions is out of bounds on this board.
     * @param pos to be judged
     * @return if pos is out of bounds
     */
    boolean outOfBounds(Position pos){
        return !(pos.getRow() < BOARD_SIZE && pos.getCell() < BOARD_SIZE &&
                pos.getRow() >= 0 && pos.getCell() >= 0);
    }

    /**
     * Returns true if the player of the specified color has a possible jump move
     * @param color of player that might have jump move
     * @return if color has a valid jump move
     */
    public boolean hasJumpMove(Piece.Color color){
        Piece.Color otherColor = color == Piece.Color.RED ? Piece.Color.WHITE : Piece.Color.WHITE;
        Position start;
        for(int row = 0; row < BOARD_SIZE; row++) {
            for(int col = 0; col < BOARD_SIZE; col++) {
                start = new Position(row, col);
                if(board[row][col] != null && board[row][col].getColor() == color){
                    if(isValidDirection(new Move(start, new Position(row - 2, col - 2)), board[row][col]) &&
                            board[row - 2][col - 2] == null && board[row - 1][col - 1] != null &&
                            board[row - 1][col - 1].getColor() == otherColor){
                        return true;
                    }
                    if(isValidDirection(new Move(start, new Position(row - 2, col + 2)), board[row][col]) &&
                            board[row - 2][col + 2] == null && board[row - 1][col + 1] != null &&
                            board[row - 1][col + 1].getColor() == otherColor){
                        return true;
                    }
                    if(isValidDirection(new Move(start, new Position(row + 2, col + 2)), board[row][col]) &&
                            board[row + 2][col + 2] == null && board[row + 1][col + 1] != null &&
                            board[row + 1][col + 1].getColor() == otherColor){
                        return true;
                    }
                    if(isValidDirection(new Move(start, new Position(row + 2, col - 2)), board[row][col]) &&
                            board[row + 2][col - 2] == null && board[row + 1][col - 1] != null &&
                            board[row + 1][col - 1].getColor() == otherColor){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns true if the move is in a valid direction
     * @param move
     * @return
     */
    public boolean isValidDirection(Move move, Piece piece){

        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCell();
        int diff = endCol - startCol;

        Piece.Color color = piece.getColor();
        Piece.Type type = piece.getType();

        if(endRow < 0 || endRow >= BOARD_SIZE || endCol < 0 || endCol >= BOARD_SIZE)
            return false;
        if(type == Piece.Type.KING)
            return true;

        if(diff < 0)
            return color == Piece.Color.RED;
        else
            return color == Piece.Color.WHITE;

    }

}
