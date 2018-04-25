package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.ReplayManager;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class GetReplayRouteTest {
    private GetReplayRoute route;
    private TemplateEngine templateEngine;
    private Request request;
    private Response response;
    private Session session;
    private ReplayManager replayManager;
    private Player currentPlayer;
    private Player otherPlayer;
    private Queue<Move> moves;
    private Game game;

    @BeforeEach
    void setup() {
        // Initialize objects
        templateEngine = mock(TemplateEngine.class);
        replayManager = new ReplayManager(new HashMap<>());
        route = new GetReplayRoute(templateEngine, replayManager);
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        currentPlayer = new Player("currentPlayer");
        otherPlayer = new Player("otherPlayer");

        // Setup replays
        moves = new LinkedList<>();
        Move move1 = new Move(new Position(5, 0), new Position(4, 1));
        move1 = new Move(move1, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        moves.add(move1);

        game = new Game(currentPlayer, otherPlayer);
        game.tryMove(move1);
        game.submitTurn();

        // Setup mocks
        when(request.session()).thenReturn(session);
    }

    @Test
    void validReplay() {
        // Setup test
        replayManager.addReplay(game, currentPlayer);
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(currentPlayer);
        when(request.queryParams(Strings.Request.REPLAY_ID)).thenReturn("0");
        when(request.queryParams(Strings.Request.MOVE_ID)).thenReturn("1");
        TemplateEngineTester templateEngineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class)))
                .thenAnswer(templateEngineTester.makeAnswer());

        // Run test
        route.handle(request, response);

        // Verify outcome
        verify(response, never()).redirect(WebServer.HOME_URL);
        templateEngineTester.assertViewModelExists();
        templateEngineTester.assertViewModelIsaMap();
        templateEngineTester.assertViewName(Strings.Template.Game.FILE_NAME);
        templateEngineTester.assertViewModelAttribute(Strings.Template.Game.CURRENT_PLAYER, currentPlayer);
        templateEngineTester.assertViewModelAttribute(Strings.Template.Game.VIEW_MODE, ViewMode.REPLAY);
        templateEngineTester.assertViewModelAttribute(Strings.Template.Game.RED_PLAYER, currentPlayer);
        templateEngineTester.assertViewModelAttribute(Strings.Template.Game.WHITE_PLAYER, otherPlayer);
        templateEngineTester.assertViewModelAttribute(Strings.Template.Game.ACTIVE_COLOR, Piece.Color.WHITE);
        templateEngineTester.assertViewModelAttribute(Strings.Template.Game.REPLAY_TOTAL_MOVES, moves.size());
    }

    @Test
    void noSession() {
        // Setup test
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(null);

        // Run test
        route.handle(request, response);

        // Verify outcome
        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    void badParams() {
        // Setup test
        replayManager.addReplay(game, currentPlayer);
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(currentPlayer);
        when(request.queryParams(Strings.Request.REPLAY_ID)).thenReturn("not a number");
        when(request.queryParams(Strings.Request.MOVE_ID)).thenReturn("not a number");

        // Run test
        route.handle(request, response);

        // Verify outcome
        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    void noReplays() {
        // Setup test
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(currentPlayer);
        when(request.queryParams(Strings.Request.REPLAY_ID)).thenReturn("0");
        when(request.queryParams(Strings.Request.MOVE_ID)).thenReturn("0");

        // Run test
        route.handle(request, response);

        // Verify outcome
        verify(response).redirect(WebServer.HOME_URL);
    }

    private void testIDs(String replayID, String moveID) {
        // Setup test
        replayManager.addReplay(game, currentPlayer);
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(currentPlayer);
        when(request.queryParams(Strings.Request.REPLAY_ID)).thenReturn(replayID);
        when(request.queryParams(Strings.Request.MOVE_ID)).thenReturn(moveID);

        // Run test
        route.handle(request, response);

        // Verify outcome
        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    void invalidReplayID() {
        testIDs("-1", "0");
    }

    @Test
    void invalidMoveID() {
        testIDs("0", "-1");
    }

    @Test
    void outOfBoundsReplayID() {
        testIDs("10", "0");
    }

    @Test
    void outOfBoundsMoveID() {
        testIDs("0", "10");
    }
}
