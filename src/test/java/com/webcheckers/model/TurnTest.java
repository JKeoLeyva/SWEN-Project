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
            turn = new Turn(board, Piece.Color.RED);
            Position start = new Position(5, i);
            Position end = new Position(4, i+1);
            Move move = new Move(start, end);
            Message result = turn.tryMove(move);
            assertEquals(result.getType(), Message.Type.info);
            queue.add(move);
            moves.add(turn.getValidatedMoves().peek());

            turn = new Turn(board, Piece.Color.WHITE);
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
            assertTrue(turn.getValidatedMoves().isEmpty());
        }
    }

    /**
     * Attempts to make a jump move, then several invalid moves.
     */
    @Test
    void testJumpMove() {
        Position start = new Position(5, 0);
        Position middle = new Position(4,1);
        Position end = new Position(3, 2);
        Position nextEnd = new Position(2, 3);

        board.setPiece(nextEnd, null);
        board.setPiece(middle, new Piece(Piece.Type.KING, Piece.Color.WHITE));
        turn = new Turn(board, Piece.Color.RED);
        Message ret = turn.tryMove(new Move(start, end));
        assertEquals(Message.Type.info, ret.getType());
        assertEquals(Turn.VALID_MOVE, ret.getText());
        // Test a single move after a jump move.
        Move move = new Move(end, nextEnd);
        ret = turn.tryMove(move);
        assertEquals(Message.Type.error, ret.getType());
        assertEquals(Turn.ALREADY_JUMPING, ret.getText());
        // Test a jump move with a different piece than the starting piece.
        move = new Move(middle, nextEnd);
        ret = turn.tryMove(move);
        assertEquals(Message.Type.error, ret.getType());
        assertEquals(Turn.JUMP_CHANGE, ret.getText());
        // Test a jump move over an empty spot.
        board = new Board();
        board.setPiece(middle, null);
        turn = new Turn(board, Piece.Color.RED);
        move = new Move(start, end);
        ret = turn.tryMove(move);
        assertEquals(Message.Type.error, ret.getType());
        assertEquals(Turn.BAD_JUMP, ret.getText());
        // Test q jump move over a piece of the wrong color.
        board = new Board();
        board.setPiece(middle, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        turn = new Turn(board, Piece.Color.RED);
        move = new Move(start, end);
        ret = turn.tryMove(move);
        assertEquals(Message.Type.error, ret.getType());
        assertEquals(Turn.BAD_JUMP, ret.getText());
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


    /**
     * Tests some out of bounds moves.
     */
    @Test
    void outOfBounds(){
        Position in = new Position(0, 0);
        Position out = new Position(-1, 0);
        Move move = new Move(in, out);
        Message ret = turn.tryMove(move);
        assertEquals(Message.Type.error, ret.getType());
        assertEquals(Turn.BAD_MOVE, ret.getText());
        move = new Move(out, in);
        ret = turn.tryMove(move);
        assertEquals(Message.Type.error, ret.getType());
        assertEquals(Turn.BAD_MOVE, ret.getText());
    }

    /**
     * Tests if a King was trying to make jump moves back and forth.
     */
    @Test
    void jumpBackAndForth(){
        //First, perform a valid jump move.
        Position start = new Position(5, 0);
        Position middle = new Position(4,1);
        Position end = new Position(3, 2);
        board.setPiece(middle, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        board.setPiece(start, new Piece(Piece.Type.KING, Piece.Color.RED));
        turn = new Turn(board, Piece.Color.RED);
        turn.tryMove(new Move(start, end));
        // Trying to jump back.
        Message ret = turn.tryMove(new Move(end, start));
        assertEquals(Message.Type.error, ret.getType());
        assertEquals(Turn.JUMPED_OVER, ret.getText());
    }

    /**
     * Tests if the space you are moving onto is occupied.
     */
    @Test
    void occupiedSpace(){
        Position start = new Position(5, 0);
        Position end = new Position(4,1);
        board.setPiece(end, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        turn = new Turn(board, Piece.Color.RED);
        Message ret = turn.tryMove(new Move(start, end));
        assertEquals(Message.Type.error, ret.getType());
        assertEquals(Turn.SPACE_OCCUPIED, ret.getText());
    }

    /**
     * Test requiring a forced move
     */
    @Test
    void forcedMove() {
        // Setup test
        board.setPiece(new Position(4, 1), new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        turn = new Turn(board, Piece.Color.RED);
        Move move = new Move(new Position(5, 2), new Position(4, 3));

        // Run test
        Message message = turn.tryMove(move);

        // Verify results
        assertEquals(new Message(Turn.FORCED_MOVE, Message.Type.error), message);
    }
}
