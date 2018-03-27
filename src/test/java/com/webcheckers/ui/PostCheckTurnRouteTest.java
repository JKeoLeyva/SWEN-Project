package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PostCheckTurnRouteTest {
    private GameManager gameManager;
    private PostCheckTurnRoute route;
    private Gson gson;
    private Request request;
    private Response response;

    @BeforeEach
    void setup() {
        gameManager = mock(GameManager.class);
        gson = mock(Gson.class);
        request = mock(Request.class);
        response = mock(Response.class);

        route = new PostCheckTurnRoute(gameManager, gson);
    }

    @Test
    void returnsTrueInfoMessageForRedPlayer() throws Exception {
        String message = (String) route.handle(request, response);

        Message m = gson.fromJson(message, Message.class);
        assertEquals();
        assertTrue();
    }
}
