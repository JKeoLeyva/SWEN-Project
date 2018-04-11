package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Checks if an entered sign-in name is acceptable and signs in the player
 */
public class PostSigninRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /signin} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine.
     * @param playerLobby    a list of all currently logged-in players.
     */
    public PostSigninRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSigninRoute is invoked.");

        final Session session = request.session();
        final String playerName = request.queryParams(Strings.Template.SignIn.PLAYER_NAME);

        // If the given player name has non-alphaNumeric characters.
        boolean hasNonAlpha = playerName.matches("^.*[^a-zA-Z0-9 ].*$");

        if(hasNonAlpha) {
            // Name is not valid.
            return templateEngine.render(new ModelAndView(new HashMap<>(), Strings.Template.SignIn.FILE_NAME));
        }

        if(playerLobby.isNameAvailable(playerName)) {
            // Redirect to the homepage, now signed-in.
            Player newPlayer = playerLobby.signInPlayer(playerName);
            session.attribute(Strings.Session.PLAYER, newPlayer);
            response.redirect(WebServer.HOME_URL);
            return null;
        }
        else {
            // Name already taken, reload this page.
            return templateEngine.render(new ModelAndView(new HashMap<>(), Strings.Template.SignIn.FILE_NAME));
        }
    }
}
