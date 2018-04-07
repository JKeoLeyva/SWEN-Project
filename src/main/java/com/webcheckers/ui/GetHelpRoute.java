package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;
import java.util.logging.Logger;

public class GetHelpRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHelpRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameManager gameManager;

    public GetHelpRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby,
                        final GameManager gameManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("GetHelpRoute is invoked.");

        return null;

    }
}
