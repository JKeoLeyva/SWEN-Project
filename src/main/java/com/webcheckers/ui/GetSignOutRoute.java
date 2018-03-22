package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.logging.Logger;

public class GetSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /signout} HTTP request
     */
    public GetSignOutRoute(final PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
        LOG.config("GetSignOutRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        Player player = session.attribute(PostSigninRoute.PLAYER_ATTR);

        if(player != null) {
            // TODO: resign game if in a game
            playerLobby.signOutPlayer(player.getName());
            session.invalidate();
        }

        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
