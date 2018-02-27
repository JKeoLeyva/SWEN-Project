package com.webcheckers.ui;

/**
 * @author Jacob Keegan
 */

import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

public class PostGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());
    private final TemplateEngine templateEngine;
    private GameManager gameManager;

    public static final String MESSAGE_ATTR = "message";
    private static final String ALREADY_IN_GAME_ERROR = "Player is already in a game.";

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

        String redirect = WebServer.GAME_URL;

        if(!gameManager.addBoard(player1, player2)) {
            redirect = WebServer.HOME_URL;
            session.attribute(MESSAGE_ATTR, new Message(ALREADY_IN_GAME_ERROR, Message.Type.error));
        }

        response.redirect(redirect);
        halt();
        return null;
    }
}
