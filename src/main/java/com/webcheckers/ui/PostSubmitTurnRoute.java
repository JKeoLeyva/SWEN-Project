package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import spark.*;

public class PostSubmitTurnRoute implements Route {

    private GameManager gameManager;
    private PlayerLobby playerLobby;

    public PostSubmitTurnRoute(final GameManager gameManager, final PlayerLobby playerLobby) {
        this.gameManager = gameManager;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();

        return null;
    }
}
