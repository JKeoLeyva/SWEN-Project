package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class PostBackupMoveRoute implements Route{
    private final static Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private final Gson gson;
    private final GameManager gameManager;

    public PostBackupMoveRoute(Gson gson, GameManager gameManager){
        this.gson = gson;
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostBackupMoveRoute is invoked.");
        Session session = request.session();
        Player currPlayer = session.attribute(Strings.Session.PLAYER);
        gameManager.getGame(currPlayer).backupMove();

        Message message = new Message("Last move undone.", Message.Type.info);
        return gson.toJson(message, Message.class);
    }
}
