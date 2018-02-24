package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
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
     *
     * @param templateEngine
     *   the HTML template rendering engine
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

        System.out.println(playerName);
        System.out.println(playerLobby.isNameAvailable(playerName));

        if(playerLobby.isNameAvailable(playerName)) {
            Player newPlayer = playerLobby.signInPlayer(playerName);
            session.attribute(PLAYER_ATTR, newPlayer);
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Home - " + playerName);
            return templateEngine.render(new ModelAndView(vm , "home.ftl"));
        }
        else{
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Sign in");
            return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
        }
    }
}
