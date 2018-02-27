package com.webcheckers.ui;

/**
 * @author Jacob Keegan
 */

import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Board;
import spark.*;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private GameManager gameManager;

    public PostGameRoute(final TemplateEngine templateEngine,
                         final GameManager gameManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.gameManager = gameManager;
        LOG.config("PostGameRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostGameRoute is invoked.");
        final Session session = request.session();
        final Player player1 = session.attribute(PostSigninRoute.PLAYER_ATTR);
        final Player player2 = new Player(request.queryParams("opponent"));
        final Board newBoard = new Board(player1, player2);

        this.gameManager.createBoard(player1.getName(), newBoard);
        this.gameManager.createBoard(player2.getName(), newBoard);
        response.redirect(WebServer.GAME_URL);
        halt();
        return null;
    }
}
