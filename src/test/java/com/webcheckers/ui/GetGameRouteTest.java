package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.ReplayManager;
import com.webcheckers.model.Player;
import com.webcheckers.model.ViewMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by kac9868 on 3/28/2018.
 */
@Tag("UI-tier")
class GetGameRouteTest {
    private TemplateEngineTester engineTester;

    private GameManager gameManager;
    private Request request;
    private Response response;
    private Session session;
    private Player player1;
    private Player player2;
    private TemplateEngine templateEngine;
    private GetGameRoute route;
    private BoardView boardview;
    private ReplayManager replayManager;

    @BeforeEach
    void setup() {
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.session = mock(Session.class);
        this.gameManager = new GameManager(new HashMap<>());
        this.player1 = new Player("Dank");
        this.player2 = new Player("Memes");
        this.replayManager = new ReplayManager(new HashMap<>());

        gameManager.createGame(player1, player2);

        this.boardview = mock(BoardView.class);
        this.templateEngine = mock(TemplateEngine.class);

        route = new GetGameRoute(templateEngine, gameManager, replayManager);

        when(request.session()).thenReturn(session);
        when(request.session().attribute(Strings.Session.PLAYER)).thenReturn(player1);

        engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    }

    @Test
    void openGame() {
        session.attribute(Strings.Session.PLAYER, player1);

        route.handle(request, response);
        //verify(response, times(1)).redirect(WebServer.GAME_URL);

        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(Strings.Template.Game.CURRENT_PLAYER, player1);
        engineTester.assertViewModelAttribute(Strings.Template.Game.VIEW_MODE, ViewMode.PLAY);
        engineTester.assertViewModelAttribute(Strings.Template.Game.RED_PLAYER, gameManager.getGame(player1).getRedPlayer());
        engineTester.assertViewModelAttribute(Strings.Template.Game.WHITE_PLAYER, gameManager.getGame(player1).getWhitePlayer());
        engineTester.assertViewModelAttribute(Strings.Template.Game.ACTIVE_COLOR, gameManager.getGame(player1).getActiveColor());
        //engineTester.assertViewModelAttribute(Strings.Template.Game.BOARD, gameManager.getGame(player1).makeBoardView(player1));
    }
}
