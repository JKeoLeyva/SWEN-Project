package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostGameRouteTest {

    private GameManager gameManager;
    private Request request;
    private Response response;
    private Session session;
    private Player player1;
    private Player player2;
    private TemplateEngine templateEngine;
    private PostGameRoute route;

    @BeforeEach
    void setUp() {
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.session = mock(Session.class);
        this.gameManager = new GameManager();
        this.player1 = new Player("Ayy");
        this.player2 = new Player(("Lmao"));

        route = new PostGameRoute(gameManager);

        when(request.session()).thenReturn(session);
        when(request.session().attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(player1);
        when(request.queryParams("opponent")).thenReturn(player2.getName());
    }

    @Test
    void createFirstGame() {
        route.handle(request, response);
        assertFalse(gameManager.canCreateGame(player1, player2));
        assertTrue(!gameManager.getGame(player1).equals(null));

        verify(response).redirect(WebServer.GAME_URL);
    }

    @Test
    void gameAlreadyExists() {
        gameManager.createGame(player1, player2);
        route.handle(request, response);
        assertFalse(gameManager.canCreateGame(player1, player2));
        //assertNotNull(session.attribute(PostGameRoute.MESSAGE_ATTR));

        verify(response).redirect(WebServer.HOME_URL);
    }
}
