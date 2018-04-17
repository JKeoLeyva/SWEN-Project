package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

/**
 * Spark Route used to handle when a move is made by a Player
 */
public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());
    private GameManager gameManager;
    private Gson gson;

    /**
     * @param gson Used to submit a turn to the Game
     * @param gameManager used to determine the current game
     */
    public PostSubmitTurnRoute(final Gson gson, final GameManager gameManager) {
        this.gameManager = gameManager;
        this.gson = gson;
    }

    /**
     * @param request contains the current Player
     * @param response redirects to the generated game Board
     * @return Json Object
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSubmitTurnRoute is invoked.");
        Session session = request.session();
        Player currPlayer = session.attribute(Strings.Session.PLAYER);
        Game game = gameManager.getGame(currPlayer);

        return gson.toJson(game.submitTurn());
    }
}
