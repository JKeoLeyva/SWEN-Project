package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
class PostCheckTurnRouteTest {

    private PostCheckTurnRoute route;
    private Gson gson;
    private GameManager gameManager;
    private Request request;
    private Player player1;
    private Player player2;
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

        this.player1 = new Player("Karl");
        this.player2 = new Player("Mark");

        when(request.session()).thenReturn(session);
        when(request.session().attribute(Strings.Session.PLAYER)).thenReturn(player1);

        route = new PostCheckTurnRoute(gameManager, gson);
    }

    @Test
    void isMyTurn() throws Exception {
        gameManager.createGame(player1, player2);

        String jsonMessage = (String) route.handle(request, response);
        Message message = gson.fromJson(jsonMessage, Message.class);

        assertEquals(message.getType(), Message.Type.info);
    }

    @Test
    void playerDoesNotExist() {
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(null);

        Object a = route.handle(request, response);
        verify(response).redirect(WebServer.HOME_URL);
        assertNull(a);
    }

    @Test
    void opponentResigns() {
        Player player1 = new Player("Karl");
        Player player2 = new Player("Mark");

        gameManager.createGame(player1, player2);
        gameManager.deleteGame(player2);

        String jsonMessage = (String) route.handle(request, response);
        Message message = gson.fromJson(jsonMessage, Message.class);

        assertEquals(message.getText(), String.valueOf(true));
    }

    @Test
    void redPlayerResigns() {
        Player player1 = new Player("Karl");
        Player player2 = new Player("Mark");

        gameManager.createGame(player2, player1);
        gameManager.deleteGame(player2);

        String jsonMessage = (String) route.handle(request, response);
        Message message = gson.fromJson(jsonMessage, Message.class);

        assertEquals(message.getText(), String.valueOf(true));
    }
}
