package com.webcheckers.model;

import com.webcheckers.appl.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("Model-tier")
class TurnTest {
    private Board board;
    private Turn turn;

    @BeforeEach
    void setup(){
        board = new Board();
        turn = new Turn(board, Piece.Color.RED);
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
            turn.setPlayerColor(Piece.Color.RED);
            Position start = new Position(5, i);
            Position end = new Position(4, i+1);
            Move move = new Move(start, end);
            Message result = turn.tryMove(move);
            assertEquals(result.getType(), Message.Type.info);
            queue.add(move);
            moves.add(turn.getValidatedMoves().get(0));

            turn.setPlayerColor(Piece.Color.WHITE);
            start = new Position(2, i+1);
            end = new Position(3, i);
            move = new Move(start, end);
            result = turn.tryMove(move);
            assertEquals(result.getType(), Message.Type.info);
            queue.add(move);
            moves.add(turn.getValidatedMoves().get(0));
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
            assertTrue(turn.getValidatedMoves().isEmpty());
        }
    }

    /**
     * Attempts to make a jump move
     */
    @Test
    void testJumpMove() {
        Position start = new Position(5, 0);
        Position end = new Position(3, 2);
        board.setPiece(new Position(4,1), new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        turn = new Turn(board, Piece.Color.RED);
        Message ret = turn.tryMove(new Move(start, end));
        assertEquals(ret.getType(), Message.Type.info);
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
     * Attempts to make Red and White pieces move to invalid spaces.
     */
    @Test
    void whiteSpace(){
        Position start = new Position(5, 0);
        Position end = new Position(5, 1);
        Message ret = turn.tryMove(new Move(start, end));
        assertEquals(new Message(Turn.INVALID_DISTANCE, Message.Type.error), ret);

        turn = new Turn(board, Piece.Color.RED);
        start = new Position(2, 1);
        end = new Position(3, 1);
        ret = turn.tryMove(new Move(start, end));
        assertEquals(new Message(Turn.INVALID_DISTANCE, Message.Type.error), ret);
    }

    @Test
    void jumpAfterSingle() {
        Move singleMove = new Move(
                new Position(5, 0),
                new Position(4, 1)
        );

        Move jumpMove = new Move(
                new Position(4, 1),
                new Position(2, 3)
        );

        Turn turn = new Turn(board, Piece.Color.RED);
        Message singleMessage = turn.tryMove(singleMove);
        assertEquals(Message.Type.info, singleMessage.getType());

        Message jumpMessage = turn.tryMove(jumpMove);
        assertEquals(new Message(Turn.MOVE_ALREADY_MADE, Message.Type.error), jumpMessage);
    }

    @Test
    void doubleJump() {
        Move jump1 = new Move(
                new Position(5, 0),
                new Position(3, 2)
        );

        Move jump2 = new Move(
                new Position(3, 2),
                new Position(1, 4)
        );

        board.setPiece(new Position(4, 1), new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        board.setPiece(new Position(1, 4), null);

        Turn turn = new Turn(board, Piece.Color.RED);
        Message message1 = turn.tryMove(jump1);
        assertEquals(Message.Type.info, message1.getType());

        Message message2 = turn.tryMove(jump2);
        assertEquals(Message.Type.info, message2.getType());
    }
}
