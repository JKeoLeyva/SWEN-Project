package com.webcheckers.appl;

// @author Jacob Keegan
// A class for text messages from the server.

public class Message {
    //An application-specific enum for messages.
    protected enum messageType{
        info, error
    }

    private messageType type;
    private String text;

    public Message(String text, messageType type) {
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
    public messageType getType() {
        return type;
    }

}
