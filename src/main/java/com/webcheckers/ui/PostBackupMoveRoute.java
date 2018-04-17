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

/**
 * Spark Route to revert the state of the Checkers board so that a move was undone
 */
public class PostBackupMoveRoute implements Route{
    private final static Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private final Gson gson;
    private final GameManager gameManager;

    /**
     *
     * @param gson Used to return a message to the User indicating a move was undone
     * @param gameManager Used to get the game associated with the current player
     */
    public PostBackupMoveRoute(Gson gson, GameManager gameManager){
        this.gson = gson;
        this.gameManager = gameManager;
    }

    /**
     * @param request contains the name of the Player who wants to undo a move
     * @param response used to redirect to a generated checkers board.
     * @return Null
     */
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
