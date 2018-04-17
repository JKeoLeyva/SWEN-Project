package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.ReplayManager;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

/**
 * Spark Route used to handle when a Player Resigns from a game
 */
public class PostResignRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());
    private static final String FAILURE_MSSG = "Resign Failed";
    private static final String SUCCESS_MSSG = "Resign Successful";

    private final GameManager gameManager;
    private final Gson gson;
    private final ReplayManager replayManager;

    /**
     * @param gameManager used to get the game session to quit
     * @param replayManager used to add a Replay to the current User
     */
    public PostResignRoute(final GameManager gameManager, final ReplayManager replayManager) {
        this.gameManager = gameManager;
        this.gson = new Gson();
        this.replayManager = replayManager;
    }

    /**
     * @param request contains the name of the current user
     * @param response used to redirect to the Home page
     * @return Json Object
     */
    public Object handle(Request request, Response response) {
        LOG.finer("PostResignRoute is invoked.");
        Player player = request.session().attribute(Strings.Session.PLAYER);
        Message message;
        replayManager.addReplay(gameManager.getGame(player), player);
        gameManager.deleteGame(player);

        if(!gameManager.getGames().containsKey(player)) {
            message = new Message(SUCCESS_MSSG, Message.Type.info);
        }
        else {
            message = new Message(FAILURE_MSSG, Message.Type.error);
        }

        return gson.toJson(message);
    }
}
