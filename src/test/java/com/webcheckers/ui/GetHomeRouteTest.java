package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class GetHomeRouteTest {
    // Class under test
    private GetHomeRoute route;

    // Template Engine test helper
    private TemplateEngineTester engineTester;

    // Test strings
    private static final String MESSAGE_TEXT = "test_message";

    // Mocked objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private Player player;
    private Message message;
    private GameManager gameManager;

    /**
     * Set up the mocked objects and some method calls
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        player = mock(Player.class);
        gameManager = mock(GameManager.class);
        message = mock(Message.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(null);
        when(session.attribute(PostGameRoute.MESSAGE_ATTR)).thenReturn(null);
        when(gameManager.getBoard(player)).thenReturn(null);
        when(message.getText()).thenReturn(MESSAGE_TEXT);

        route = new GetHomeRoute(templateEngine, playerLobby, gameManager);

        // Setup template tester
        engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    }

    /**
     * Test the no session scenario
     */
    @Test
    public void noSession() {
        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, never()).redirect(WebServer.GAME_URL);

        // Verify data sent to template engine
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewName(GetHomeRoute.VIEW);
        engineTester.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, "Welcome!");
        engineTester.assertViewModelAttribute(GetHomeRoute.CURRENT_PLAYER_ATTR, null);
        engineTester.assertViewModelAttribute(GetHomeRoute.PLAYER_LOBBY_ATTR, playerLobby);
        engineTester.assertViewModelAttribute(GetHomeRoute.GAME_MANAGER_ATTR, gameManager);
        engineTester.assertViewModelAttributeIsAbsent(GetHomeRoute.MESSAGE_ATTR);
    }

    /**
     * Test the signed in (but waiting) player home
     */
    @Test
    public void waitingPlayer() {
        // Add current player
        when(session.attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(player);

        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, never()).redirect(WebServer.GAME_URL);

        // Verify data sent to template engine
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, "Welcome!");
        engineTester.assertViewModelAttribute(GetHomeRoute.CURRENT_PLAYER_ATTR, player);
        engineTester.assertViewModelAttribute(GetHomeRoute.PLAYER_LOBBY_ATTR, playerLobby);
        engineTester.assertViewModelAttributeIsAbsent(GetHomeRoute.MESSAGE_ATTR);
    }

    /**
     * Test the redirect from home to game when the player is in a game
     */
    @Test
    public void inGame() {
        // Add current player (in a game)
        Board board = mock(Board.class);
        when(session.attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(player);
        when(gameManager.getBoard(player)).thenReturn(board);

        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, times(1)).redirect(WebServer.GAME_URL);
    }

    /**
     * Test the message functionality
     */
    @Test
    public void message() {
        // Add message to session
        when(session.attribute(PostGameRoute.MESSAGE_ATTR)).thenReturn(message);

        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, never()).redirect(WebServer.GAME_URL);

        // Verify data sent to template engine
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, "Welcome!");
        engineTester.assertViewModelAttribute(GetHomeRoute.CURRENT_PLAYER_ATTR, null);
        engineTester.assertViewModelAttribute(GetHomeRoute.PLAYER_LOBBY_ATTR, playerLobby);
        engineTester.assertViewModelAttribute(GetHomeRoute.MESSAGE_ATTR, MESSAGE_TEXT);
    }
}
