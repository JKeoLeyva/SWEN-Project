package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayManager;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.Mockito.*;

@Tag("UI-tier")
class GetHomeRouteTest {
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
    private ReplayManager replayManager;

    /**
     * Set up the mocked objects and some method calls
     */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        player = mock(Player.class);
        gameManager = mock(GameManager.class);
        message = mock(Message.class);
        replayManager = mock(ReplayManager.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(null);
        when(session.attribute(Strings.Session.MESSAGE)).thenReturn(null);
        when(gameManager.getGame(player)).thenReturn(null);
        when(message.getText()).thenReturn(MESSAGE_TEXT);

        route = new GetHomeRoute(templateEngine, playerLobby, gameManager, replayManager);

        // Setup template tester
        engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    }

    /**
     * Test the no session scenario
     */
    @Test
    void noSession() {
        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, never()).redirect(WebServer.GAME_URL);

        // Verify data sent to template engine
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewName(Strings.Template.Home.FILE_NAME);
        engineTester.assertViewModelAttribute(Strings.Template.Home.CURRENT_PLAYER, null);
        engineTester.assertViewModelAttribute(Strings.Template.Home.PLAYER_LOBBY, playerLobby);
        engineTester.assertViewModelAttribute(Strings.Template.Home.GAME_MANAGER, gameManager);
        engineTester.assertViewModelAttributeIsAbsent(Strings.Template.Home.MESSAGE);
    }

    /**
     * Test the signed in (but waiting) player home
     */
    @Test
    void waitingPlayer() {
        // Add current player
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(player);

        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, never()).redirect(WebServer.GAME_URL);

        // Verify data sent to template engine
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(Strings.Template.Home.CURRENT_PLAYER, player);
        engineTester.assertViewModelAttribute(Strings.Template.Home.PLAYER_LOBBY, playerLobby);
        engineTester.assertViewModelAttributeIsAbsent(Strings.Template.Home.MESSAGE);
    }

    /**
     * Test the redirect from home to game when the player is in a game
     */
    @Test
    void inGame() {
        // Add current player (in a game)
        Game game = mock(Game.class);
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(player);
        when(gameManager.getGame(player)).thenReturn(game);

        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, times(1)).redirect(WebServer.GAME_URL);
    }

    /**
     * Test the message functionality
     */
    @Test
    void message() {
        // Add message to session
        when(session.attribute(Strings.Session.MESSAGE)).thenReturn(message);

        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, never()).redirect(WebServer.GAME_URL);

        // Verify data sent to template engine
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(Strings.Template.Home.CURRENT_PLAYER, null);
        engineTester.assertViewModelAttribute(Strings.Template.Home.PLAYER_LOBBY, playerLobby);
        engineTester.assertViewModelAttribute(Strings.Template.Home.MESSAGE, MESSAGE_TEXT);
    }
}
