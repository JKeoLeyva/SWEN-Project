package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * Spark Route used to check a turn submitted by a Player
 */
public class PostCheckTurnRoute implements Route {
    private final static Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());
    private GameManager gameManager;
    private Gson gson;

    /**
     * @param gameManager used to get the game in which the move was submitted to
     * @param gson used to deliver a message to the User after this route is done
     */
    public PostCheckTurnRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
    }

    /**
     * @param request contains the name of the current Player
     * @param response used to redirect to the generated board
     * @return
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostCheckTurnRoute is invoked.");
        Player player = request.session().attribute(Strings.Session.PLAYER);

        if(player == null) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        Game game = gameManager.getGame(player);
        if(game == null || game.isGameOver())
            return gson.toJson(new Message("true", Message.Type.info));

        String outcome = String.valueOf(game.isMyTurn(player));
        Message message = new Message(outcome, Message.Type.info);

        return gson.toJson(message);
    }
}
