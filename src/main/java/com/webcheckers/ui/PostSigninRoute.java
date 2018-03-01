package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * Checks if an entered sign-in name is acceptable.
 * @author sam davis sjd8078
 */
public class PostSigninRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    public static final String PLAYER_ATTR = "currPlayer";


    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /signin} HTTP request.
     * @param templateEngine the HTML template rendering engine.
     * @param playerLobby a list of all currently logged-in players.
     */
    public PostSigninRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;

        LOG.config("PostSigninRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.finer("PostSigninRoute is invoked.");

        final Session session = request.session();

        final String playerName = request.queryParams("name");

        // If the given player name has non-alphaNumeric characters.
        boolean hasNonAlpha = playerName.matches("^.*[^a-zA-Z0-9 ].*$");

        if(hasNonAlpha){
            // Name is not valid.
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Sign in");
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }

        if(playerLobby.isNameAvailable(playerName)) {
            // Redirect to the homepage, now signed-in.
            Player newPlayer = playerLobby.signInPlayer(playerName);
            session.attribute(PLAYER_ATTR, newPlayer);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        else {
            // Name already taken, reload this page.
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Sign in");
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }
    }
}
