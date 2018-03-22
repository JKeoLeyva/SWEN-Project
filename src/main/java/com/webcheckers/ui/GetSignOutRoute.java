package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

public class GetSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    static final String PLAYER_PARAM = "player";

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
    public Object handle(Request request, Response response) throws Exception {
        String playerName = request.queryParams(PLAYER_PARAM);
        Player player = request.session().attribute(PostSigninRoute.PLAYER_ATTR);

        // Ignore unauthenticated sign-outs
        if(!player.getName().equals(playerName))
            return null;

        // TODO: resign game if in a game
        playerLobby.signOutPlayer(playerName);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
