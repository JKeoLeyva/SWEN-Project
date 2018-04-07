package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.Message;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameManager gameManager;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request
     *
     * @param templateEngine the HTML template rendering engine
     * @param playerLobby    a list of all current players
     * @param gameManager    a list of all current games
     */
    public GetHomeRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby,
                        final GameManager gameManager) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");
        Player currentPlayer = null;

        final Session httpSession = request.session();
        if(httpSession.attribute(Strings.Session.PLAYER) != null) {
            currentPlayer = httpSession.attribute(Strings.Session.PLAYER);
        }

        if(gameManager.getGame(currentPlayer) != null) {
            // The current player is in a game, and sent to it.
            response.redirect(WebServer.GAME_URL);
            return null;
        }

        Message message = httpSession.attribute(Strings.Session.MESSAGE);

        Map<String, Object> vm = new HashMap<>();
        vm.put(Strings.Template.Home.CURRENT_PLAYER, currentPlayer);
        vm.put(Strings.Template.Home.PLAYER_LOBBY, playerLobby);
        vm.put(Strings.Template.Home.GAME_MANAGER, gameManager);

        if(message != null) {
            // Some message exists, and should be displayed.
            vm.put(Strings.Template.Home.MESSAGE, message.getText());
            httpSession.removeAttribute(Strings.Session.MESSAGE);
        }

        return templateEngine.render(new ModelAndView(vm, Strings.Template.Home.FILE_NAME));
    }

}