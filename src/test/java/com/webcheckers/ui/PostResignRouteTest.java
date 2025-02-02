package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.ReplayManager;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@Tag("UI-tier")
class PostResignRouteTest {

    private PostResignRoute route;
    private TemplateEngineTester engineTester;
    private Request request;
    private Response response;
    private Session session;
    private GameManager gameManager;
    private Player player;
    private ReplayManager replayManager;

    /*
     * Set up the mocked objects and some method calls
     * */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        player = mock(Player.class);
        gameManager = mock(GameManager.class);
        replayManager = mock(ReplayManager.class);

        when(request.session()).thenReturn(session);

        route = new PostResignRoute(gameManager, replayManager);
    }

    /*
     * Test the no session scenario
     *
     */
    @Test
    void noSession() {
        try {
            route.handle(request, response);
        } catch(Exception e) {
            fail(e.getMessage());
        }
        verify(response, never()).redirect(WebServer.HOME_URL);
    }

    /*
    * Test a successful resign
    */
    @Test
    void successfulResign(){
        try{
            route.handle(request, response);
        }catch(Exception e){
            fail(e.getMessage());
        }
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(player);
    }

}