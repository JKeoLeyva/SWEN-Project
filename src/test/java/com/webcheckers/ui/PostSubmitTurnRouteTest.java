package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostSubmitTurnRouteTest {
    private GameManager gameManager;
    private Request request;
    private Response response;
    private Gson gson;
    private Session session;
    private PostSubmitTurnRoute route;

    @BeforeEach
    void setUp() {
        this.request = mock(Request.class);
        this.gson = new Gson();
        this.response = mock(Response.class);
        this.session = mock(Session.class);
        this.gameManager = mock(GameManager.class);

        this.route = new PostSubmitTurnRoute(gson, gameManager);

        when(request.session()).thenReturn(session);
    }

    @Test
    void testPlacingValidMove() {
        route.handle(request, response);
    }
}
