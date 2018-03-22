package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.Mockito.*;


@Tag("UI-tier")
public class PostSigninRouteTest {

    private PostSigninRoute route;
    private TemplateEngineTester engineTester;
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Player player;

    /*
    * Set up the mocked objects and some method calls
    * */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        templateEngine = mock(TemplateEngine.class);
        player = mock(Player.class);
        playerLobby = mock(PlayerLobby.class);

        when(request.session()).thenReturn(session);

        route = new PostSigninRoute(templateEngine, playerLobby);

        engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    }
    /*

    * Test the no session scenario
    *
    * */
    @Test
    void noSession() {
        try {
            route.handle(request, response);
        } catch(Exception e) {

        }

        verify(response, never()).redirect(WebServer.SIGNIN_URL);
    }

    /*
    * Test a successful log in attempt
    *
    * */
    @Test
    void testLoggingIn() {
        String testUsername = "testUser";
        request.params().put("name", testUsername);

        try {
            route.handle(request, response);
        } catch(Exception e) {

        }

        when(session.attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(player);
        //verify(response, times(1)).redirect(WebServer.HOME_URL);
    }

    /*
    * Test login attempt with invalid username
    *
    * */
    @Test
    void testInvalidUsernameLogin() {
        String invalidUsername = "!@Test.";
        request.params().put("name", invalidUsername);

        try {
            route.handle(request, response);
        } catch(Exception e) {

        }

        when(session.attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(null);
    }
}
