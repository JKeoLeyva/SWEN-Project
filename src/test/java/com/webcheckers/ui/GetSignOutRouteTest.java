package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static com.webcheckers.ui.GetSignOutRoute.PLAYER_PARAM;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class GetSignOutRouteTest {
    private Request request;
    private Response response;
    private Session session;
    private Player player;
    private PlayerLobby playerLobby;
    private String playerName = "player";

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
    }

    /**
     * Expect signing out the current player to work
     */
    @Test
    void signOutSelf() throws Exception {
        GetSignOutRoute route = new GetSignOutRoute(playerLobby);

        when(session.attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(player);
        when(request.queryParams(PLAYER_PARAM)).thenReturn(playerName);

        route.handle(request, response);

        verify(session, times(1)).attribute(PostSigninRoute.PLAYER_ATTR);
        verify(playerLobby, times(1)).signOutPlayer(playerName);
        verify(response, times(1)).redirect(WebServer.HOME_URL);
    }

    /**
     * Expect signing out a different player to fail
     */
    @Test
    void signOutOther() throws Exception {
        GetSignOutRoute route = new GetSignOutRoute(playerLobby);
        Player otherPlayer = new Player("otherPlayer");

        when(session.attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(otherPlayer);

        route.handle(request, response);

        verify(session, times(1)).attribute(PostSigninRoute.PLAYER_ATTR);
        verify(playerLobby, never()).signOutPlayer(playerName);
        verify(response, never()).redirect(WebServer.HOME_URL);
    }
}
