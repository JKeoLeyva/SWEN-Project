package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostBackupMoveRoute implements Route{
    private final Gson gson;
    private final GameManager gameManager;

    public PostBackupMoveRoute(Gson gson, GameManager gameManager){
        this.gson = gson;
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player currPlayer = session.attribute(PostSigninRoute.PLAYER_ATTR);
        Game game = gameManager.getGame(currPlayer);
        game.reverseMove();

        Message message = new Message("Last move undone.", Message.Type.info);
        return gson.toJson(message, Message.class);
    }
}
