package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.ViewMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by kac9868 on 3/28/2018.
 */
public class GetGameRouteTest {
    private TemplateEngineTester engineTester;

    private GameManager gameManager;
    private Request request;
    private Response response;
    private Session session;
    private Player player1;
    private Player player2;
    private TemplateEngine templateEngine;
    private GetGameRoute route;

    @BeforeEach
    void setUp() {
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.session = mock(Session.class);
        this.gameManager = new GameManager();
        this.player1 = mock(Player.class);
        this.player2 = mock(Player.class);
        this.templateEngine = mock(TemplateEngine.class);

        route = new GetGameRoute(templateEngine, gameManager);

        when(request.session()).thenReturn(session);
        when(request.session().attribute(PostSigninRoute.PLAYER_ATTR)).thenReturn(null);

        engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    }

    @Test
    void name() {
        gameManager.createGame(player1, player2);
        request.session().attribute(PostSigninRoute.PLAYER_ATTR, player1);

        route.handle(request, response);
        //verify(response, times(1)).redirect(WebServer.GAME_URL);

        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
        engineTester.assertViewModelAttribute("title", "Game");
        engineTester.assertViewModelAttribute("currentPlayer", player1);
        engineTester.assertViewModelAttribute("viewMode", ViewMode.PLAY);
        engineTester.assertViewModelAttribute("redPlayer", gameManager.getGame(player1).getRedPlayer());
        engineTester.assertViewModelAttribute("whitePlayer", gameManager.getGame(player1).getWhitePlayer());
        engineTester.assertViewModelAttribute("activeColor", gameManager.getGame(player1).getActiveColor());
        engineTester.assertViewModelAttribute("board", gameManager.getGame(player1).makeBoardView(player1));
    }
}
