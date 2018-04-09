package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayManager;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

import static spark.Spark.halt;

public class GetSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final PlayerLobby playerLobby;
    private final GameManager gameManager;
    private final ReplayManager replayManager;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /signout} HTTP request
     */
    public GetSignOutRoute(final PlayerLobby playerLobby, final GameManager gameManager, final ReplayManager replayManager) {
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
        this.replayManager = replayManager;
    }

    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(Strings.Session.PLAYER);

        if(player != null) {
            if(gameManager.getGame(player) != null) {
                gameManager.deleteGame(player);
                replayManager.deleteReplay(player);
            }
            playerLobby.signOutPlayer(player.getName());
            session.invalidate();
        }

        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
