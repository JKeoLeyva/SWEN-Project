package com.webcheckers.model;

import com.webcheckers.appl.Message;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    private static final Message INVALID_DISTANCE_MESSAGE =
            new Message("You can only move forward 1 space diagonally.", Message.Type.error);
    private static final Message TAKEN_SPACE_MESSAGE =
            new Message("You can only move onto an empty space.", Message.Type.error);
    private static final Message VALID_MOVE_MESSAGE =
            new Message("Move is valid!", Message.Type.info);
    private Player player1 = new Player("player1");
    private Player player2 = new Player("player2");
    private Board board = new Board(player1, player2);
    private Move CuT;

    // Clears board for testing.
    @BeforeAll
    void setup(){
        for(int row = 0; row < Board.BOARD_SIZE; row++){
            for(int col = 0; col < Board.BOARD_SIZE; col++) {
                board.setPiece(row, col, null);
            }
        }
    }

    @Test
    void spaceIsTaken() {
        for(int col = 0; col < Board.BOARD_SIZE; col+=2) {
            board.setPiece(3, col, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
            board.setPiece(4, col + 1, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        }
        for(int col = 0; col < Board.BOARD_SIZE; col+=2){
            Position whitePiece = new Position(3, col);
            Position redPiece = new Position(4, col+1);
            CuT = new Move(whitePiece, redPiece);
            Message result = CuT.isValid(board);
            assertEquals(result, TAKEN_SPACE_MESSAGE);
            CuT = new Move(redPiece, whitePiece);
            result = CuT.isValid(board);
            assertEquals(result, TAKEN_SPACE_MESSAGE);
        }
    }

    @Test
    void invalidDistance(){
        for(int col = 0; col < Board.BOARD_SIZE; col+=2) {
            board.setPiece(3, col, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
            board.setPiece(4, col + 1, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        }
        for(int col = 0; col < Board.BOARD_SIZE; col+=2) {
            Position pos1 = new Position(1, col);
        }
    }
}
