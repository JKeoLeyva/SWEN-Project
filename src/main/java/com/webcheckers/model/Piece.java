package com.webcheckers.model;

// @author Jacob Keegan
// A class representing a checkers piece.

import com.webcheckers.appl.Message;

public class Piece {
    public enum pieceType{
        SINGLE, KING
    }
    public enum pieceColor{
        RED, WHITE
    }

    private pieceType type;
    private pieceColor color;

    public Piece(pieceType type, pieceColor color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Returns what type of piece this is.
     * @return the type
     */
    public pieceType getType() {
        return type;
    }

    /**
     * Returns what color this piece is.
     * @return the color
     */
    public pieceColor getColor() {
        return color;
    }
}
