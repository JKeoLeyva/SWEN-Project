package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostSubmitTurnRoute implements Route {

    private GameManager gameManager;
    private PlayerLobby playerLobby;
    private Gson gson;

    public PostSubmitTurnRoute(final Gson gson, final GameManager gameManager) {
        this.gameManager = gameManager;
        this.playerLobby = playerLobby;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player currPlayer = session.attribute(PostSigninRoute.PLAYER_ATTR);
        Game game = gameManager.getGame(currPlayer);
        Move move = session.attribute("currentMove");
        game.makeMove(move);
        game.switchTurn();

        Message result = new Message("true", Message.Type.info);

        if(game.isGameOver(currPlayer)) {
            //remove the game from the game manager
            response.redirect(WebServer.HOME_URL);
        }

        return gson.toJson(result);
    }
}
