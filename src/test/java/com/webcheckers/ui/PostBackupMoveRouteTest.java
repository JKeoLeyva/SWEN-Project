package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
class PostBackupMoveRouteTest {
    @Test
    void testBackupMove() {
        // Initialize mocks and objects
        Gson gson = new Gson();
        Player player = new Player("player");
        GameManager gameManager = mock(GameManager.class);
        Game game = mock(Game.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        Session session = mock(Session.class);
        PostBackupMoveRoute route = new PostBackupMoveRoute(gson, gameManager);

        // Setup mocks
        when(request.session()).thenReturn(session);
        when(session.attribute(Strings.Session.PLAYER)).thenReturn(player);
        when(gameManager.getGame(player)).thenReturn(game);

        // Run test
        String actualString = (String) route.handle(request, response);

        // Verify output
        Message expectedMessage = new Message("Last move undone.", Message.Type.info);
        Message actualMessage = gson.fromJson(actualString, Message.class);

        assertEquals(expectedMessage, actualMessage);

        // Verify mocks
        verify(game, times(1)).backupMove();
    }
}
