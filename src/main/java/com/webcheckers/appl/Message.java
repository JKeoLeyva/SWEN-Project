package com.webcheckers.appl;

// @author Jacob Keegan
// A class for text messages from the server.

public class Message {
    //An application-specific enum for messages.
    public enum type{
        info, error
    }

    private type type;
    private String text;

    public Message(String text, type type) {
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
    public type getType() {
        return type;
    }

}
