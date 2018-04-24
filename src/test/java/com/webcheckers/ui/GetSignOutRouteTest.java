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

@Tag("UI-tier")
class GetSignOutRouteTest {
    private Request request;
    private Response response;
    private Session session;
    private Player player;
    private PlayerLobby playerLobby;
    private String playerName = "player";
    private GetSignOutRoute route;

    /**
     * Set up the mocked objects and method calls
     */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        player = new Player(playerName);
        playerLobby = mock(PlayerLobby.class);

        when(request.session()).thenReturn(session);
        route = new GetSignOutRoute(playerLobby,
                new GameManager(new HashMap<>()),
                new ReplayManager(new HashMap<>()));
    }

    /**
     * Expect signing out the current player to work
     */
    @Test
    void signOutSelf() {
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(player);

        route.handle(request, response);

        verify(session, times(1)).attribute(Strings.Session.PLAYER);
        verify(session, times(1)).invalidate();
        verify(playerLobby, times(1)).signOutPlayer(playerName);
        verify(response, times(1)).redirect(WebServer.HOME_URL);
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
        verify(playerLobby, never()).signOutPlayer(playerName);
        verify(response, times(1)).redirect(WebServer.HOME_URL);
    }
}
