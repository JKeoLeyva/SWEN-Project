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
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
        this.templateEngine = mock(TemplateEngine.class);
        gameManager.createGame(player1, player2);

        route = new GetGameRoute(templateEngine, gameManager, replayManager);
        when(request.session()).thenReturn(session);

        engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    }

    /**
     * Basically, tests reloading of /game when it's your turn.
     */
    @Test
    void openGame() {
        when(request.session().attribute(Strings.Session.PLAYER)).thenReturn(player1);
        route.handle(request, response);

        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute(Strings.Template.Game.CURRENT_PLAYER, player1);
        engineTester.assertViewModelAttribute(Strings.Template.Game.VIEW_MODE, ViewMode.PLAY);
        engineTester.assertViewModelAttribute(Strings.Template.Game.RED_PLAYER, gameManager.getGame(player1).getRedPlayer());
        engineTester.assertViewModelAttribute(Strings.Template.Game.WHITE_PLAYER, gameManager.getGame(player1).getWhitePlayer());
        engineTester.assertViewModelAttribute(Strings.Template.Game.ACTIVE_COLOR, gameManager.getGame(player1).getActiveColor());
    }

    /**
     * Tests when a player is not in a game.
     */
    @Test
    void noGame(){
        Player noGamePlayer = new Player("JJ");
        when(request.session().attribute(Strings.Session.PLAYER)).thenReturn(noGamePlayer);
        route.handle(request, response);

        verify(response, times(1)).redirect(WebServer.HOME_URL);
    }

    /**
     * Tests when a game has ended.
     */
    @Test
    void gameOver(){
        when(request.session().attribute(Strings.Session.PLAYER)).thenReturn(player2);
        gameManager.deleteGame(player1);
        route.handle(request, response);

        verify(response, times(1)).redirect(WebServer.HOME_URL);
        // Checks if both Players have been removed from their games.
        assertTrue(gameManager.canCreateGame(player1, player2));
    }

    /**
     * Tests when a player asks for help.
     */
    @Test
    void askHelp(){
        when(request.session().attribute(Strings.Session.PLAYER)).thenReturn(player1);
        when(request.queryParams(Strings.Request.HELP)).thenReturn("");

        route.handle(request, response);
        engineTester.assertViewModelAttributeIsPresent(Strings.Template.Game.HELP_SPACES);
    }

    /**
     * Tests when it is not your turn.
     */
    @Test
    void notYourTurn(){
        when(request.session().attribute(Strings.Session.PLAYER)).thenReturn(player2);
        route.handle(request, response);
        // Model-View should be generated, but no help was requested.
        engineTester.assertViewModelExists();
        engineTester.assertViewModelAttributeIsAbsent(Strings.Template.Game.HELP_SPACES);
    }
}
