package com.webcheckers.appl;

/**
 * @author Jacob Keegan
 * A class for text messages from the server.
 */

public class Message {
    //An application-specific enum for messages.
    public enum Type {
        info, error
    }

    private Type type;
    private String text;

    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    /**
     * Getter for message text.
     * @return message text
     */
    public String getText() {
        return text;
    }

    /**
     * Getter for message type.
     * @return message type
     */
    public Type getType() {
        return type;
    }
}
