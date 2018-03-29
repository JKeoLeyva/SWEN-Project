package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.logging.Logger;

public class PostResignRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

    //private final TemplateEngine templateEngine;
    //private final PlayerLobby playerLobby;
    private final GameManager gameManager;
    private final String FAILURE_MSSG = "Resign Failed";
    private final String SUCCESS_MSSG = "Resign Successful";
    private final Gson gson;


    public PostResignRoute(final GameManager gameManager){
        //this.templateEngine = templateEngine;
        //this.playerLobby = playerLobby;
        this.gameManager = gameManager;

        gson = new Gson();

        LOG.config("PostResignRoute is initialized");
    }

    public Object handle(Request request, Response response){
        Player player = request.session().attribute(PostSigninRoute.PLAYER_ATTR);
        Message message;

        Game game = gameManager.getGame(player);
        game.setGameOver();

        gameManager.deleteGame(player);

        if(!gameManager.getGames().containsKey(player)){
            message = new Message(SUCCESS_MSSG, Message.Type.info);
        }else{
            message = new Message(FAILURE_MSSG, Message.Type.error);
        }



        return gson.toJson(message);
    }
}
