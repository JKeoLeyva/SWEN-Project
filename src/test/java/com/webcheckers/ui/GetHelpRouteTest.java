package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class GetHelpRouteTest {

    private TemplateEngineTester engineTester;

    private GameManager gameManager;
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;
    private GetHelpRoute route;
    private Player player;

    @BeforeEach
    void setup() {
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.session = mock(Session.class);
        this.gameManager = new GameManager(new HashMap<>());
        this.player = new Player("player");

        this.templateEngine = mock(TemplateEngine.class);

        route = new GetHelpRoute(templateEngine);

        when(request.session()).thenReturn(session);

        engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    }

    @Test
    void noSession() {
        route.handle(request, response);
        assertNull(session.attribute(Strings.Session.PLAYER));
        verify(response, never()).redirect(WebServer.HELP_URL);
    }

    @Test
    void withSession() {
        session.attribute(Strings.Session.PLAYER, player);

        when(request.session().attribute(Strings.Session.PLAYER)).thenReturn(player);

        route.handle(request, response);

        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();

        engineTester.assertViewModelAttribute(Strings.Template.Game.CURRENT_PLAYER, player);

    }
}
