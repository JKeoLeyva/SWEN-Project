package com.webcheckers.model;

/**
 * A class to represent a single checkers piece.
 */
public class Piece {
    public enum Type {
        SINGLE, KING
    }

    public enum Color {
        RED, WHITE
    }

    private Type type;
    private Color color;

    /**
     * Create a new Piece
     *
     * @param t the type of piece
     * @param c the color of the piece
     */
    public Piece(Type t, Color c) {
        this.type = t;
        this.color = c;
    }

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    // For potential testing purposes.
    @Override
    public String toString() {
        String t_string = this.type == Type.SINGLE ? "Single" : "King";
        String color_string = this.color == Color.RED ? "Red" : "White";

        return color_string + " " + t_string;
    }
}
