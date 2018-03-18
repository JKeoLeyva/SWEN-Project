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
    }

    @Test
    public void noSession() {
        // Setup template tester
        TemplateEngineTester engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());

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
        engineTester.assertViewModelAttributeIsAbsent(GetHomeRoute.MESSAGE_ATTR);
    }

    @Test
    public void waitingPlayer() {
        // Setup template tester
        TemplateEngineTester engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());

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

    @Test
    public void inGame() {
        // Setup template tester
        TemplateEngineTester engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());

        // Add current player (in a game)
        Board board = mock(Board.class);
        when(session.attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(player);
        when(gameManager.getBoard(player)).thenReturn(board);

        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, times(1)).redirect(WebServer.GAME_URL);
    }
}
