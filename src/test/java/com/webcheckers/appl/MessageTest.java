package com.webcheckers.appl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Application-tier")
public class MessageTest {
    private static Message errorMessage;
    private static Message infoMessage;

    private static final String ERROR_TEXT = "error text";
    private static final String INFO_TEXT = "info text";

    @BeforeAll
    public static void setup() {
        errorMessage = new Message(ERROR_TEXT, Message.Type.error);
        infoMessage = new Message(INFO_TEXT, Message.Type.info);
    }

    @Test
    public void checkText() {
        assertEquals(ERROR_TEXT, errorMessage.getText());
        assertEquals(INFO_TEXT, infoMessage.getText());
    }

    @Test
    public void checkType() {
        assertEquals(Message.Type.error, errorMessage.getType());
        assertEquals(Message.Type.info, infoMessage.getType());
    }
}
