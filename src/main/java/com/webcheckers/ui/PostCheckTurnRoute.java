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

import static spark.Spark.halt;

public class PostCheckTurnRoute implements Route {
    private GameManager gameManager;
    private Gson gson;

    public PostCheckTurnRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        Player player = request.session().attribute(Strings.Session.PLAYER);

        if(player == null) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        Game game = gameManager.getGame(player);
        if(game == null || game.isGameOver(player))
            return gson.toJson(new Message("true", Message.Type.info));

        String outcome = String.valueOf(game.isMyTurn(player));
        Message message = new Message(outcome, Message.Type.info);

        return gson.toJson(message);
    }
}
