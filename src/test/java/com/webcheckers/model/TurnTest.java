package com.webcheckers.model;

import com.webcheckers.appl.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class TurnTest {
    private Board board;
    private Turn turn;

    @BeforeEach
    void setup(){
        board = new Board();
        turn = new Turn(board);
    }

    /**
     * Tests that many valid moves are made properly.
     */
    @Test
    void testValidMoves() {
        // Stores all moves made.
        Queue<Move> queue = new LinkedList<>();
        // Stores all moves made, by calling getValidatesMoves.
        ArrayList<Move> moves = new ArrayList<>();
        // Alternates between advancing red and white pieces.
        for(int i = 0; i < Board.BOARD_SIZE; i+=2){
            Position start = new Position(5, i);
            Position end = new Position(4, i+1);
            Move move = new Move(start, end);
            Message result = turn.tryMove(move);
            assertEquals(result.getType(), Message.Type.info);
            queue.add(move);
            moves.add(turn.getValidatedMoves().peek());

            start = new Position(2, i+1);
            end = new Position(3, i);
            move = new Move(start, end);
            result = turn.tryMove(move);
            assertEquals(result.getType(), Message.Type.info);
            queue.add(move);
            moves.add(turn.getValidatedMoves().peek());
        }

        // Checks that all moves input to isValid and all moves stored are the same.
        for(Move move : moves)
            assertEquals(move, queue.remove());
        assertTrue(queue.isEmpty());
    }

    /**
     * Advances all Red pieces in the front row,
     * then reverses the move and checks that it is reversed.
     */
    @Test
    void backupMove() {
        for(int i = 0; i < Board.BOARD_SIZE; i+=2){
            Position start = new Position(5, i);
            Position end = new Position(4, i+1);
            Move move = new Move(start, end);
            Message result = turn.tryMove(move);
            assertEquals(result.getType(), Message.Type.info);

            turn.backupMove();
            assertTrue(turn.getValidatedMoves().empty());
        }
    }

    /**
     * Attempts to make a move that moves too far.
     */
    @Test
    void testInvalidMoves(){
        Position start = new Position(5, 0);
        Position end = new Position(3, 2);
        Message ret = turn.tryMove(new Move(start, end));
        assertEquals(ret.getType(), Message.Type.error);

    }

    /**
     * Attempts to make 2 moves in a row.
     */
    @Test
    void twoMoves(){
        Position start = new Position(5, 0);
        Position end = new Position(4, 1);
        turn.tryMove(new Move(start, end));
        Message ret = turn.tryMove(new Move(end, start));
        assertEquals(ret.getType(), Message.Type.error);
    }

    /**
     * Attempts to move to an occupied space.
     */
    @Test
    void occupiedSpace(){
        board.setPiece(3, 0, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        board.setPiece(4, 1, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        Position start = new Position(4,1);
        Position end = new Position(3, 0);
        turn = new Turn(board);
        Message ret = turn.tryMove(new Move(start, end));
        assertEquals(ret.getType(), Message.Type.error);
    }

    /**
     * Attempts to make Red and White pieces move to invalid spaces.
     */
    @Test
    void whiteSpace(){
        Position start = new Position(5, 0);
        Position end = new Position(5, 1);
        Message ret = turn.tryMove(new Move(start, end));
        assertEquals(ret.getType(), Message.Type.error);
        turn = new Turn(board);
        start = new Position(2, 1);
        end = new Position(3, 1);
        ret = turn.tryMove(new Move(start, end));
        assertEquals(ret.getType(), Message.Type.error);
    }

}
