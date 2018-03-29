package com.webcheckers.ui;

import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class GetSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final PlayerLobby playerLobby;
    private final GameManager gameManager;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /signout} HTTP request
     */
    public GetSignOutRoute(final PlayerLobby playerLobby, final GameManager gameManager) {
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
        LOG.config("GetSignOutRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(PostSigninRoute.PLAYER_ATTR);

        if(player != null) {
            if(gameManager.getGame(player) != null){
                Game game = gameManager.getGame(player);
                game.setGameOver();
                gameManager.deleteGame(player);
            }
            playerLobby.signOutPlayer(player.getName());
            session.invalidate();
        }

        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
