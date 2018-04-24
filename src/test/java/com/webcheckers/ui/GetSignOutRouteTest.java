package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayManager;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("UI-tier")
class GetSignOutRouteTest {
    private Request request;
    private Response response;
    private Session session;
    private PlayerLobby playerLobby;
    private GameManager gameManager;
    private Player player1 = new Player("Bob");
    private Player player2 = new Player("by");
    private GetSignOutRoute route;

    /**
     * Set up the mocked objects and method calls
     */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        playerLobby = mock(PlayerLobby.class);
        gameManager = new GameManager(new HashMap<>());
        gameManager.createGame(player1, player2);

        when(request.session()).thenReturn(session);
        route = new GetSignOutRoute(playerLobby, gameManager,
                new ReplayManager(new HashMap<>()));
    }

    /**
     * Expect signing out the current player to work
     */
    @Test
    void signOutSelf() {
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(player1);
        route.handle(request, response);
        assertTrue(gameManager.canCreateGame(player1, new Player("")));

        verify(session, times(1)).attribute(Strings.Session.PLAYER);
        verify(session, times(1)).invalidate();
        verify(playerLobby, times(1)).signOutPlayer(player1);
        verify(response, times(1)).redirect(WebServer.HOME_URL);

        // Game is removed now.
        assertNull(route.handle(request, response));
    }

    /**
     * Expect signing out when not signed in to not work
     */
    @Test
    void noSession() {
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(null);

        route.handle(request, response);

        verify(session, times(1)).attribute(Strings.Session.PLAYER);
        verify(session, never()).invalidate();
        verify(playerLobby, never()).signOutPlayer(player1);
        verify(response, times(1)).redirect(WebServer.HOME_URL);
    }
}
