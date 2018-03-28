package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostCheckTurnRouteTest {

    private PostCheckTurnRoute route;
    private Gson gson;
    private GameManager gameManager;
    private Request request;
    private Response response;
    private Session session;

    /*
    * Set up mocked objects and method calls
    *
    * */
    @BeforeEach
    void setUp() {
        this.gson = new Gson();
        this.gameManager = new GameManager();
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.session = mock(Session.class);

        route = new PostCheckTurnRoute(gameManager, gson);

        when(request.session()).thenReturn(session);
    }

    @Test
    void isMyTurn() throws Exception {
        Player player1 = new Player("Karl");
        Player player2 = new Player("Mark");

        gameManager.createGame(player1, player2);
        when(session.attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(player1);

        String jsonMessage = (String) route.handle(request, response);
        Message message = gson.fromJson(jsonMessage, Message.class);

        assertEquals(message.getType(), Message.Type.info);
    }
}
