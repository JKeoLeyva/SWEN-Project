package com.webcheckers;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.WebServer;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;

public class WebServerTest {
    @Test
    void testServerStarts() {
        // Setup mocks and objects
        Gson gson = new Gson();
        PlayerLobby playerLobby = new PlayerLobby();
        GameManager gameManager = new GameManager();
        TemplateEngine templateEngine = mock(TemplateEngine.class);

        // Create WebServer
        WebServer webServer = new WebServer(templateEngine, gson, playerLobby, gameManager);

        // Make sure startup doesn't crash
        webServer.initialize();
    }
}
