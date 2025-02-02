package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

/**
 * A Spark Route for requesting a new checkers game.
 */
public class PostGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());
    private GameManager gameManager;

    private static final String ALREADY_IN_GAME_ERROR = "Player is already in a game.";

    public PostGameRoute(final GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * @param request  contains the names of bother players.
     * @param response used to redirect to a generated checkers board.
     * @return null after redirect
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostGameRoute is invoked.");
        final Session session = request.session();
        final Player player1 = session.attribute(Strings.Session.PLAYER);
        final Player player2 = new Player(request.queryParams(Strings.Request.OPPONENT));

        String redirect = WebServer.GAME_URL;

        if(!gameManager.createGame(player1, player2)) {
            // One of the players is already in a game: redirect to home page.
            redirect = WebServer.HOME_URL;
            session.attribute(Strings.Session.MESSAGE, new Message(ALREADY_IN_GAME_ERROR, Message.Type.error));
        }

        response.redirect(redirect);
        return null;
    }
}
