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
        assertEquals(errorMessage.getText(), ERROR_TEXT);
        assertEquals(infoMessage.getText(), INFO_TEXT);
    }

    @Test
    public void checkType() {
        assertEquals(errorMessage.getType(), Message.Type.error);
        assertEquals(infoMessage.getType(), Message.Type.info);
    }
}
