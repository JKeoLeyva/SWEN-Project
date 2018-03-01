package com.webcheckers.ui;

import com.webcheckers.appl.PlayerManager;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerManager playerManager;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request
     * @param templateEngine the HTML template rendering engine
     * @param playerManager the data for all games and players
     */
    public GetHomeRoute(final TemplateEngine templateEngine,
                        final PlayerManager playerManager) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerManager = playerManager;
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");
        Player currentPlayer = null;

        final Session httpSession = request.session();
        if(httpSession.attribute(PostSigninRoute.PLAYER_ATTR) != null) {
            currentPlayer = httpSession.attribute(PostSigninRoute.PLAYER_ATTR);
        }

        if(playerManager.getBoard(currentPlayer) != null) {
            // The current player is in a game, and sent to it.
            response.redirect(WebServer.GAME_URL);
        }

        Message message = httpSession.attribute(PostGameRoute.MESSAGE_ATTR);

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");
        vm.put("currentPlayer", currentPlayer);
        vm.put("playerManager", playerManager);

        if(message != null) {
            // Some message exists, and should be displayed.
            vm.put("message", message.getText());
            httpSession.removeAttribute(PostGameRoute.MESSAGE_ATTR);
        }

        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }

}