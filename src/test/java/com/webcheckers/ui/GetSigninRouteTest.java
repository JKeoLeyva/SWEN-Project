package com.webcheckers.ui;

import com.webcheckers.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
class GetSigninRouteTest {

    // Class under test
    private GetSigninRoute route;

    private TemplateEngineTester engineTester;

    // Mocked objects
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;

    /**
     * Set up the mocked objects and some method calls
     */
    @BeforeEach
    void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        templateEngine = mock(TemplateEngine.class);

        when(request.session()).thenReturn(session);

        route = new GetSigninRoute(templateEngine);

        // Setup template tester
        engineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(engineTester.makeAnswer());
    }

    /**
     * Test the no session scenario
     */
    @Test
    void noSession() {
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(null);
        // Run code
        route.handle(request, response);

        // Verify HTTP behavior
        verify(response, never()).redirect(WebServer.SIGNIN_URL);

        // Verify data sent to template engine
        engineTester.assertViewModelExists();
        engineTester.assertViewModelIsaMap();
    }

    /**
     * Test the scenario when the Player is already signed in.
     */
    @Test
    void signedIn(){
        when(session.attribute(Strings.Session.PLAYER)).thenReturn("");
        assertNull(route.handle(request, response));
    }


}