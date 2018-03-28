package com.webcheckers.model;

import com.webcheckers.appl.Message;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class StableTurnTest {

    @Test
    void isValidValidMoves() {
        Queue<Move> queue = new LinkedList<>();
        Board board = new Board();
        StableTurn stableTurn = new StableTurn(board);
        ArrayList<Move> moves = new ArrayList<>();
        for(int i = 0; i < Board.BOARD_SIZE; i+=2){
            Position start = new Position(5, i);
            Position end = new Position(4, i+1);
            Move move = new Move(start, end);
            Message result = stableTurn.isValid(move);
            assertEquals(result.getType(), Message.Type.info);
            queue.add(move);
            moves.add(stableTurn.getValidatedMoves().peek());
            stableTurn.backupMove();

            start = new Position(2, i+1);
            end = new Position(3, i);
            move = new Move(start, end);
            result = stableTurn.isValid(move);
            assertEquals(result.getType(), Message.Type.info);
            queue.add(move);
            moves.add(stableTurn.getValidatedMoves().peek());
            stableTurn.backupMove();
        }

        for(Move move : moves)
            assertEquals(move, queue.remove());
        assertTrue(queue.isEmpty());
    }

    @Test
    void backupMove() {
        Board board = new Board();
        StableTurn stableTurn = new StableTurn(board);
        for(int i = 0; i < Board.BOARD_SIZE; i+=2){
            Position start = new Position(5, i);
            Position end = new Position(4, i+1);
            Move move = new Move(start, end);
            Message result = stableTurn.isValid(move);
            assertEquals(result.getType(), Message.Type.info);

            stableTurn.backupMove();
            assertTrue(stableTurn.getValidatedMoves().empty());
        }
    }

    @Test
    void isValidInvalidMoves(){
        Board board = new Board();
        StableTurn stableTurn = new StableTurn(board);
        Position start = new Position(5, 0);
        Position end = new Position(3, 2);
        // Attempt to make a move that moves too far.
        Message ret = stableTurn.isValid(new Move(start, end));
        assertEquals(ret.getType(), Message.Type.error);
        // Attempt to make 2 moves in a row.
        end = new Position(4, 1);
        stableTurn.isValid(new Move(start, end));
        stableTurn.isValid(new Move(end, start));

        board.setPiece(3, 0, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        board.setPiece(4, 1, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        // Attempt to move to an occupied space.
        start = new Position(4,1);
        end = new Position(3, 0);
        stableTurn = new StableTurn(board);
        ret = stableTurn.isValid(new Move(start, end));
        assertEquals(ret.getType(), Message.Type.error);
        // Attempt to make Red move to an invlaid space.
        stableTurn = new StableTurn(board);
        end = new Position(4, 0);
        ret = stableTurn.isValid(new Move(start, end));
        assertEquals(ret.getType(), Message.Type.error);
        // Attempt to make White move to an invalid space.
        stableTurn = new StableTurn(board);
        start = new Position(3, 0);
        end = new Position(2, 1);
        ret = stableTurn.isValid(new Move(start, end));
        assertEquals(ret.getType(), Message.Type.error);
    }
}
