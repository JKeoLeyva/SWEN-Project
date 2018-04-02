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

public class PostResignRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());
    private static final String FAILURE_MSSG = "Resign Failed";
    private static final String SUCCESS_MSSG = "Resign Successful";

    private final GameManager gameManager;
    private final Gson gson;


    public PostResignRoute(final GameManager gameManager) {
        this.gameManager = gameManager;
        this.gson = new Gson();
    }

    public Object handle(Request request, Response response) {
        Player player = request.session().attribute(Strings.Session.PLAYER);
        Message message;

        Game game = gameManager.getGame(player);
        game.setGameOver();

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
