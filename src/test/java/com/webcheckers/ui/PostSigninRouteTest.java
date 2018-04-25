package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.Mockito.*;


@Tag("UI-tier")
class PostSigninRouteTest {
    private PostSigninRoute route;
    private Request request;
    private Response response;
    private Session session;
    private PlayerLobby playerLobby;

    /**
     * Set up the mocked objects and some method calls
     */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        TemplateEngine templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);

        when(request.session()).thenReturn(session);

        route = new PostSigninRoute(templateEngine, playerLobby);

        TemplateEngineTester engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    }

    /**
     * Test the no session scenario
     */
    @Test
    void noSession() {
        route.handle(request, response);
        verify(response, never()).redirect(WebServer.SIGNIN_URL);
    }

    /**
     * Test a valid log in attempt
     */
    @Test
    void testLoggingIn() {
        Player player = new Player("testUser");
        when(request.queryParams(Strings.Template.SignIn.PLAYER_NAME)).thenReturn(player.getName());
        when(playerLobby.isNameAvailable(anyString())).thenReturn(true);
        when(playerLobby.signInPlayer(player)).thenReturn(player);

        route.handle(request, response);

        verify(session, times(1)).attribute(Strings.Session.PLAYER, player);
        verify(playerLobby, times(1)).isNameAvailable(player.getName());
        verify(playerLobby, times(1)).signInPlayer(player);
    }

    /**
     * Test login attempt with invalid username
     */
    @Test
    void testInvalidUsernameLogin() {
        Player player = new Player("!@Test.");
        when(request.queryParams(Strings.Template.SignIn.PLAYER_NAME)).thenReturn(player.getName());

        route.handle(request, response);

        verify(session, never()).attribute(Strings.Session.PLAYER, player);
        verify(playerLobby, never()).isNameAvailable(player.getName());
        verify(playerLobby, never()).signInPlayer(player);
    }

    /**
     * Test when the name is already taken
     */
    @Test
    void testAlreadyTakenName() {
        Player player = new Player("testUser");
        when(request.queryParams(Strings.Template.SignIn.PLAYER_NAME)).thenReturn(player.getName());
        when(playerLobby.isNameAvailable(player.getName())).thenReturn(false);

        route.handle(request, response);

        verify(session, never()).attribute(Strings.Session.PLAYER, player);
        verify(playerLobby, times(1)).isNameAvailable(player.getName());
        verify(playerLobby, never()).signInPlayer(player);
    }
}
