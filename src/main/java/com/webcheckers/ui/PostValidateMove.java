package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostValidateMove implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    private final Gson gson;
    private final GameManager gameManager;

    public PostValidateMove(Gson gson, GameManager gameManager) {
        this.gson = gson;
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostValidateMove is invoked.");
        String moveJSON = request.body();
        Move move = gson.fromJson(moveJSON, Move.class);
        Session session = request.session();
        Player currPlayer = session.attribute(PostSigninRoute.PLAYER_ATTR);
        session.attribute("currentMove", move);
        Game game = gameManager.getGame(currPlayer);
        Message result = move.isValid(game.getBoard());
        return gson.toJson(result);
    }
}
