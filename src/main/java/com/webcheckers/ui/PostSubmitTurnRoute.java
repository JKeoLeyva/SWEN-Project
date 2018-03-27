package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import javafx.geometry.Pos;
import spark.*;

public class PostSubmitTurnRoute implements Route {

    private GameManager gameManager;
    private PlayerLobby playerLobby;
    private Gson gson;

    public PostSubmitTurnRoute(final Gson gson, final GameManager gameManager, final PlayerLobby playerLobby) {
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
        Position start = move.getStart();
        Position end = move.getEnd();
        Piece piece = game.getBoard().getPiece(start.getRow(), start.getCell());
        game.getBoard().setPiece(start.getRow(), start.getCell(), null);
        game.getBoard().setPiece(end.getRow(), start.getCell(), piece);

        Message result = new Message("true", Message.Type.info);
        return gson.toJson(result);
    }
}
