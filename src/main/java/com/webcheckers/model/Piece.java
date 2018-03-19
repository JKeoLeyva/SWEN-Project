package com.webcheckers.model;

/**
 * A class to represent a single checkers piece.
 */
public class Piece {
    public enum Type {
        SINGLE, KING;

        @Override
        public String toString() {
            // Uppercase first letter
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }

    public enum Color {
        RED, WHITE;

        @Override
        public String toString() {
            // Uppercase first letter
            return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
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
        String t_string = this.type.toString();
        String color_string = this.color.toString();

        return color_string + " " + t_string;
    }
}
