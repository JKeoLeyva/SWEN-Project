package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

/**
 * Spark Route used to validate a move that is being made by a Player
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    private final Gson gson;
    private final GameManager gameManager;

    /**
     * @param gson used to return the result of the move being made
     * @param gameManager Used to find the current game being played
     */
    public PostValidateMoveRoute(Gson gson, GameManager gameManager) {
        this.gson = gson;
        this.gameManager = gameManager;
    }

    /**
     * @param request contains the name of the current Player
     * @param response used to redirect to the generate game board
     * @return Json object
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostValidateMoveRoute is invoked.");
        String moveJSON = request.body();
        Move move = gson.fromJson(moveJSON, Move.class);
        Session session = request.session();
        Player currPlayer = session.attribute(Strings.Session.PLAYER);
        Message result = gameManager.getGame(currPlayer).tryMove(move);
        return gson.toJson(result);
    }
}
