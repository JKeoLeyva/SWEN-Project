package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSubmitTurnRouteTest {
    private GameManager gameManager;
    private Request request;
    private Response response;
    private Gson gson;
    private Session session;
    private Player player1;
    private Player player2;
    private PostSubmitTurnRoute route;
    private PostValidateMoveRoute validateMoveRoute;

    @BeforeEach
    void setUp() {
        this.request = mock(Request.class);
        this.gson = new Gson();
        this.response = mock(Response.class);
        this.session = mock(Session.class);
        this.gameManager = new GameManager();

        this.player1 = new Player("Abc");
        this.player2 = new Player("Xyz");

        when(request.session()).thenReturn(session);
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(player1);

        this.route = new PostSubmitTurnRoute(gson, gameManager);
        this.validateMoveRoute = new PostValidateMoveRoute(gson, gameManager);
    }

    @Test
    void testPlacingValidMove() {
        gameManager.createGame(player1, player2);

        Move move = new Move(new Position(5, 0), new Position(4, 1));
        String moveJson = gson.toJson(move);

        when(request.body()).thenReturn(moveJson);
        String validateJson = (String) validateMoveRoute.handle(request, response);
        Message validateMessage = gson.fromJson(validateJson, Message.class);
        assertEquals(validateMessage.getType(), Message.Type.info);

        String jsonMessage = (String) route.handle(request, response);
        Message message = gson.fromJson(jsonMessage, Message.class);
        assertEquals(message.getType(), Message.Type.info);
    }
}
