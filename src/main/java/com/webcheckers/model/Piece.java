package com.webcheckers.model;

public class Piece {

    private Type t;
    private Color color;

    public enum Type{
        SINGLE, KING
    }

    public enum Color{
        RED, WHITE
    }

    public Piece(Type t, Color c){
        this.t = t;
        this.color = c;
    }

    public Type getT() {
        return t;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        String t_string = this.t == Type.SINGLE ? "Single" : "King";
        String color_string = this.color == Color.RED ? "Red" : "White";

        return color_string + " " + t_string;
    }
}
